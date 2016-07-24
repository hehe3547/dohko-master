package com.dohko.core.registry.client;

import com.dohko.core.base.DataMap;
import com.dohko.core.config.ClientConfig;
import com.dohko.core.grpc.support.RpcClient;
import com.dohko.core.registry.NotifyListener;
import com.dohko.core.registry.Registry;
import com.dohko.core.services.Service;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.LogUtils;
import com.dohko.core.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiangbin on 2016/6/14.
 */
@Component("services_rpcClusterSupport")
public class RpcClusterClientSupport extends RpcClient implements NotifyListener, ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(RpcClusterClientSupport.class);

    @Autowired
    private ClientConfig configConfig;

    private String path = Service.class.getName();

    @Override
    public void shutdown() throws InterruptedException {
        Registry discoveryService = appContext.getBean(Registry.class);
        if (discoveryService == null) {
            LogUtils.error("NOT FOUND DISCOVERY_SERVICE", logger);
            return;
        }
        List<DataMap> dataList = loadDiscoryClient();
        for (DataMap dataMap : dataList) {
            discoveryService.unsubscribe(dataMap, this);
        }
        if (dataList.size() == 0) {
            LogUtils.error("not found discory service", logger);
        }
        super.shutdown();
    }

    @Override
    public void notify(DataMap clientDataMap, List<DataMap> serviceList) {
        Map<String, List<Referer>> serviceCache = new ConcurrentHashMap<>();
        Map<String, Referer> connectCache = new ConcurrentHashMap<>();
        for (DataMap serviceConfig : serviceList) {
            LogUtils.info(serviceConfig, "service notify ", logger);
            String connectKey = createConnectKey(serviceConfig);
            Referer connect = new RefererImpl(serviceConfig.getString(Registry.KEY_HOST), serviceConfig.getInteger(Registry.KEY_PORT));
            connectCache.put(connectKey, connect);
            String serviceData = serviceConfig.getString(Registry.KEY_SERVICES);
            for (String service : serviceData.split(";")) {
                List<Referer> connectList = serviceCache.get(service);
                if (connectList == null) {
                    connectList = new ArrayList<>();
                    serviceCache.put(service, connectList);
                }
                connectList.add(connect);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("refresh service [" + serviceCache + "] connect cache [" + connectCache + "]");
        }
        refresh(clientDataMap, serviceCache, connectCache);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String[] beanNames = appContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        Registry discoveryService = appContext.getBean(Registry.class);
        if (discoveryService == null) {
            LogUtils.error("NOT FOUND DISCOVERY_SERVICE", logger);
            return;
        }
        List<DataMap> dataList = loadDiscoryClient();
        for (DataMap dataMap : dataList) {
            discoveryService.subscribe(dataMap, this);
        }
        if (dataList.size() == 0) {
            LogUtils.error("not found discory service", logger);
        }
    }

    private List<DataMap> loadDiscoryClient() {
        List<DataMap> dataList = new ArrayList<>();
        String groups = configConfig.getGroups();
        if (groups != null && !"".equals(groups.trim())) {
            String[] models = groups.trim().split(";");
            for (String modelStr : models) {
                DataMap dataMap = DataMap.Builder.create()
                          .put(Registry.KEY_GROUP, modelStr).put(Registry.KEY_PATH, path).put(Registry.KEY_PORT, 0)
                          .put(Registry.KEY_HOST, NetUtils.getHostAddress());
                String dataJson = DataUtils.dataMap2json(dataMap);
                dataMap.put(Registry.KEY_DATA, dataJson);
                dataList.add(dataMap);
            }
        }
        return dataList;
    }
}
