package com.dohko.core.registry.server;

import com.dohko.core.base.DataMap;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.registry.Registry;
import com.dohko.core.services.ExecInfo;
import com.dohko.core.services.Service;
import com.dohko.core.services.service.IgnoreService;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.LogUtils;
import com.dohko.core.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by xiangbin on 2016/6/15.
 */
@Component("core_serviceRegistrySupport")
public class ServiceRegistrySupport implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(ServiceRegistrySupport.class);

    @Autowired
    private ServerConfig serverConfig;

    private String path = Service.class.getName();

    private ApplicationContext appContext;

    private DataMap loadRegistryService() {
        String host = NetUtils.getHostAddress();
        if (logger.isInfoEnabled()) {
            logger.info("registry group [" + serverConfig.getGroup() + "] path [" + path + "] host [" + host +"]  port [" + serverConfig.getPort() + "]");
        }
        Map<String, Service> services = this.appContext.getBeansOfType(Service.class);
        //注册一个管理服务
        ExecInfo execInfo = this.appContext.getBean(ExecInfo.class);
        Map<String, String> coreServices = execInfo.getCoreServices();
        StringBuilder sb = new StringBuilder();
        for (String coreService : coreServices.keySet()) {
            sb.append(coreService).append(";");
        }
        int size = 0;
        for (String service : services.keySet()) {
            if (services.get(service) instanceof IgnoreService) {
                continue;
            }
            size = size + 1;
            sb.append(service).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String serviceStr = sb.toString();
        if (logger.isInfoEnabled()) {
            logger.info("register service [" + serviceStr +"]");
        }
        //向zookeeper注册服务
        DataMap dataMap = DataMap.Builder.create().put(Registry.KEY_GROUP, serverConfig.getGroup())
                  .put(Registry.KEY_HOST, host).put(Registry.KEY_PORT, serverConfig.getPort()).put(Registry.KEY_PATH, path)
                  .put(Registry.KEY_SERVICES, serviceStr).put(Registry.KEY_SIZE, size);
        String newData = DataUtils.dataMap2json(dataMap);
        dataMap.put(Registry.KEY_DATA, newData);
        return dataMap;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Registry registryService = appContext.getBean(Registry.class);
        if (registryService == null) {
            LogUtils.error("NOT FOUND REGISTRY_SERVICE", logger);
            return;
        }
        DataMap registryData = loadRegistryService();
        if (registryData == null) {
            LogUtils.error("service size [" + 0 + "]", logger);
            return;
        }
        registryService.register(registryData);
        //发布服务
        registryService.available(null);
        LogUtils.info("export service success .dohko..", logger);
        ExecInfo execInfo = this.appContext.getBean(ExecInfo.class);
        if (execInfo != null) {
            execInfo.registryInfo(registryData);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
}
