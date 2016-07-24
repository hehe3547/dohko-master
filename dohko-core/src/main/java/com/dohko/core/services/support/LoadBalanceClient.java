package com.dohko.core.services.support;

import com.dohko.core.PlatConstants;
import com.dohko.core.PlatResultInfo;
import com.dohko.core.base.*;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiangbin on 2016/6/15.
 */
public abstract class LoadBalanceClient extends BaseClient  {

    //group--[{service}]
    private static Map<String, DataMap> groupServiceCache = new ConcurrentHashMap<>();
    //service--{{key,referer}}
    private static Map<String, Referer> connectClusterCache = new ConcurrentHashMap<>();
    //service--{{referer1,referer2}}
    private static Map<String,  List<Referer>> serviceClusterCache = new ConcurrentHashMap<>();
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    static {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            private Logger logger = LoggerFactory.getLogger("com.dld.core.connect");
            @Override
            public void run() {
                if (logger.isInfoEnabled()) {
                    logger.info("connectClusterCache [" + connectClusterCache + "]");
                    logger.info("serviceClusterCache [" + serviceClusterCache + "]");
                }
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    protected abstract void execute(Request request, Referer remoteConnect, ResponseListener listener);

    protected abstract Response execute(Request request, Referer remoteConnect);

    protected void execute(Request request, ResponseListener listener) {
        String serviceName = request.getService();
        Referer referer = selectReferer(request);
        if (referer == null) {
            LogUtils.error("not found service [" + serviceName + "] referer", logger);
            serviceClusterCache.remove(serviceName);
            listener.onCompleted(Response.Builder.create(request.getTranceId(), PlatResultInfo.SERVICE_NOT_FOUND));
            return;
        }
        execute(request, referer, listener);
    }



    protected Response execute(Request request) {
        long startTime = System.currentTimeMillis();
        String serviceName = request.getService();
        Referer referer = selectReferer(request);
        if (referer == null) {
            LogUtils.error("not found service [" + serviceName + "] referer", logger);
            serviceClusterCache.remove(serviceName);
            return Response.Builder.create(request.getTranceId(), PlatResultInfo.SERVICE_NOT_FOUND);
        }
        Response response = execute(request, referer);
        String logMsg = "service [" + serviceName +" ] tranceId [" + request.getTranceId() + "] referer [" + referer +"] cost [" + (System.currentTimeMillis() - startTime) + "]ms";
        LogUtils.info(request, response, logMsg, logger);
        return response;
    }

    protected Referer selectReferer(Request request) {
        String serviceName = request.getService();
        List<Referer> connects = getServiceReferer(serviceName, request);
        if (connects == null) {
            LogUtils.error("not found service [" + serviceName + "]", logger);
            return new ClientOldReferer();
        }
        LoadBalance loadBalance = createLoadBalance(request);
        LogUtils.debug("loadBalance type [" + loadBalance.getClass() + "]", logger);
        Referer referer = loadBalance.select(request, connects);
        if (referer == null) {
            LogUtils.error("not found service [" + serviceName + "] referer", logger);
            serviceClusterCache.remove(serviceName);
        }
        return referer;
    }



    @PreDestroy
    public void shutdown() throws InterruptedException {
        //关闭所有连接
        for (Referer connect : connectClusterCache.values()) {
            connect.shutdown();
        }
    }


    protected void refresh(DataMap clientDataMap, Map<String, List<Referer>> notifyServices, Map<String, Referer> connectCache) {
        String groupName = clientDataMap.getString(PlatConstants.KEY_GROUP);
        DataMap dataMap = groupServiceCache.get(groupName);
        if (dataMap != null) {
            DataList serviceInfoList = dataMap.getDataList("serviceInfo");
            if (serviceInfoList != null) {
                for (String service : serviceInfoList.toValues("services")) {
                    this.serviceClusterCache.remove(service);
                }
            }
            DataList connectInfoList = dataMap.getDataList("connectInfo");
            if (connectInfoList != null) {
                for (String connKey : connectInfoList.toValues("connect")) {
                    Referer refer = this.connectClusterCache.remove(connKey);
                    if (refer != null) {
                        LogUtils.info("shutdown connectKey [" + refer + "]", logger);
                        refer.shutdown();
                    }
                }
            }
        } else {
            dataMap = DataMap.Builder.create();
            groupServiceCache.put(groupName, dataMap);
        }
        Set<String> keySet = notifyServices.keySet();
        List serviceInfoList = new ArrayList();
        List connectInfoList = new ArrayList();
        for (String key : keySet) {
            serviceInfoList.add(key);
            this.serviceClusterCache.put(key, notifyServices.get(key));
        }
        keySet = connectCache.keySet();
        for (String key : keySet) {
            connectInfoList.add(key);
            Referer referer = connectCache.get(key);
            referer.start();
            this.connectClusterCache.put(key, referer);
        }
        if (logger.isInfoEnabled()) {
            logger.info("groupName [" + groupName + "] connectInfo [" + connectInfoList + "] serviceInfo [" + serviceInfoList + "]");
        }
        dataMap.put("serviceInfo", DataList.Builder.create().addValues("services", serviceInfoList));
        dataMap.put("connectInfo", DataList.Builder.create().addValues("connect", connectInfoList));
    }

    public static Map<String, List<Referer>> getServiceClusterCache() {
        return serviceClusterCache;
    }

    protected String createConnectKey(DataMap dataMap) {
        return dataMap.getString(PlatConstants.KEY_HOST) + ":" + dataMap.getIntValue(PlatConstants.KEY_PORT);
    }

    private List<Referer> getServiceReferer(String serviceName, Request request) {
        return serviceClusterCache.get(pattern(serviceName));
    }

    protected String pattern(String serviceName) {
        return serviceName;
    }

    protected LoadBalance createLoadBalance(Request request) {
        return new RandomLoadBalance();
    }

    protected interface LoadBalance {
        Referer select(Request request, List<Referer> connectList);
    }

    class RandomLoadBalance implements LoadBalance {

        @Override
        public Referer select(Request request, List<Referer> connectList) {
            int size = connectList.size();
             if (size == 0){
                return null;
            }
            int idx = (int) (Math.random() * connectList.size());
            return connectList.get(idx);
        }
    }

    protected interface Referer {
        void start();
        void shutdown();
    }

    class ClientOldReferer implements Referer {

        @Override
        public void start() {

        }

        @Override
        public void shutdown() {

        }
    }


}
