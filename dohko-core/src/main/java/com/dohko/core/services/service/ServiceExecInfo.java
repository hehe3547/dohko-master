package com.dohko.core.services.service;

import com.dohko.core.PlatConstants;
import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.services.ExecInfo;
import com.dohko.core.services.annotation.AppService;
import com.dohko.core.services.support.BaseServiceSupport;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当前模块服务的统计
 * Created by xiangbin on 2016/6/15.
 */
@AppService("core_serviceExecInfo")
public class ServiceExecInfo extends BaseServiceSupport implements ExecInfo, IgnoreService {

    private Map<String, AtomicInteger> execTimesCache = new ConcurrentHashMap<>();

    @Autowired
    private ServerConfig serverConfig;

    private Map<String, String> coreServices = new ConcurrentHashMap<>();

    @Override
    public ResultInfo execute(DataMap dataMap) {
        Set<String> keySet = execTimesCache.keySet();
        DataList dataMapList = DataList.Builder.create();
        for (String key : keySet) {
            DataMap serviceData = DataMap.Builder.create().put("service", key)
                      .put("execTimes", execTimesCache.get(key).intValue());
            dataMapList.add(serviceData);
        }
        return dataMapList;
    }

    @Override
    public void serviceInfo(String service, DataMap dataMap) {
        if (execTimesCache.containsKey(service)) {
            execTimesCache.get(service).incrementAndGet();
        }
    }

    @Override
    public void registryInfo(DataMap dataMap) {
        String data = dataMap.getString(PlatConstants.KEY_DATA);
        DataMap serviceInfo = DataUtils.json2dataMap(data);
        String services = serviceInfo.getString(PlatConstants.KEY_SERVICES);
        String[] serviceArray = services.split(";");
        for (String service : serviceArray) {
            execTimesCache.put(service, new AtomicInteger(0));
        }
    }

    @Override
    public Map<String, String> getCoreServices() {
        if (coreServices.isEmpty()) {
            coreServices.put("manager_" + serverConfig.getGroup() + "_" + NetUtils.getHostAddress() + "_" + serverConfig.getPort(), "core_serviceExecInfo");
            coreServices.put("simple_" + serverConfig.getGroup() + "_" + NetUtils.getHostAddress() + "_" + serverConfig.getPort(), "core_simpleService");
        }
        return coreServices;
    }
}
