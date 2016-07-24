package com.dohko.core.registry.support;

import com.dohko.core.base.DataMap;
import com.dohko.core.config.RegistryConfig;
import com.dohko.core.registry.NotifyListener;
import com.dohko.core.registry.Registry;
import com.dohko.core.registry.RegistryManager;
import com.dohko.core.services.Service;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.LogUtils;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiangbin on 2016/6/14.
 */
@Component("core_zkRegistrySupport")
public class RegistrySupport implements Registry, RegistryManager, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(RegistrySupport.class);
    private static String REGISTRY_NAMESPACE = "/hll";
    private static String PATH_SEPARATOR = "/";
    private ConcurrentHashMap<String, ConcurrentHashMap<NotifyListener, IZkChildListener>> urlListeners = new ConcurrentHashMap<>();
    private List<DataMap> registryCache = new ArrayList<>();

    @Autowired
    private RegistryConfig registryConfig;

    private ZkClient zkClient;

    @PostConstruct
    public void init() {
        if (zkClient == null) {
            LogUtils.warn("registry zookeeper address [" + registryConfig.getAddress() + "]", logger);
            zkClient = new ZkClient(registryConfig.getAddress());
        }
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    @Override
    public void register(DataMap dataMap) {
        if (dataMap == null || dataMap.isEmpty()) {
            LogUtils.error("registry dataMap is empty", logger);
            return;
        }
        LogUtils.info(dataMap, "registry service ", logger);
        removeNode(dataMap, ZkNodeType.AVAILABLE_SERVER);
        removeNode(dataMap, ZkNodeType.UNAVAILABLE_SERVER);
        createNode(dataMap, ZkNodeType.UNAVAILABLE_SERVER);
        registryCache.add(dataMap);
    }

    @Override
    public void unregister(DataMap dataMap) {
        if (dataMap == null || dataMap.isEmpty()) {
            LogUtils.warn("registry dataMap is empty", logger);
            return;
        }
        removeNode(dataMap, ZkNodeType.AVAILABLE_SERVER);
        removeNode(dataMap, ZkNodeType.UNAVAILABLE_SERVER);
    }

    @Override
    public void available(DataMap dataMap) {
        if (dataMap == null) {
            for (DataMap u : getRegisteredServices()) {
                removeNode(u, ZkNodeType.AVAILABLE_SERVER);
                removeNode(u, ZkNodeType.UNAVAILABLE_SERVER);
                createNode(u, ZkNodeType.AVAILABLE_SERVER);
            }
        } else {
            removeNode(dataMap, ZkNodeType.AVAILABLE_SERVER);
            removeNode(dataMap, ZkNodeType.UNAVAILABLE_SERVER);
            createNode(dataMap, ZkNodeType.AVAILABLE_SERVER);
        }
    }

    @Override
    public void unavailable(DataMap dataMap) {
        if (dataMap == null) {
            for (DataMap u : getRegisteredServices()) {
                removeNode(u, ZkNodeType.AVAILABLE_SERVER);
                removeNode(u, ZkNodeType.UNAVAILABLE_SERVER);
                createNode(u, ZkNodeType.UNAVAILABLE_SERVER);
            }
        } else {
            removeNode(dataMap, ZkNodeType.AVAILABLE_SERVER);
            removeNode(dataMap, ZkNodeType.UNAVAILABLE_SERVER);
            createNode(dataMap, ZkNodeType.UNAVAILABLE_SERVER);
        }
    }

    @Override
    public List<DataMap> discover(DataMap dataMap) {
        return null;
    }

    @Override
    public List<DataMap> getRegisteredServices() {
        return registryCache;
    }

    @Override
    public void subscribe(final DataMap dataMap, final NotifyListener notifyListener) {
        final String key = toNodePath(dataMap, ZkNodeType.CLIENT);
        ConcurrentHashMap<NotifyListener, IZkChildListener> childChangeListeners = urlListeners.get(key);
        if (childChangeListeners == null) {
            urlListeners.putIfAbsent(key, new ConcurrentHashMap<NotifyListener, IZkChildListener>());
            childChangeListeners = urlListeners.get(key);
        }
        IZkChildListener zkChildListener = childChangeListeners.get(notifyListener);
        if (zkChildListener == null) {
            childChangeListeners.putIfAbsent(notifyListener, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) {
                    LogUtils.info(String.format("service list change: path=%s, currentChilds=%s", key, currentChilds), logger);
                    RegistrySupport.this.notify(dataMap, notifyListener, nodeChildsToUrls(parentPath, currentChilds));
                }
            });
            zkChildListener = childChangeListeners.get(notifyListener);
        }

        removeNode(dataMap, ZkNodeType.CLIENT);
        createNode(dataMap, ZkNodeType.CLIENT);
        String serverTypePath = toNodeTypePath(dataMap, ZkNodeType.AVAILABLE_SERVER);
        List<String> currentChilds = zkClient.subscribeChildChanges(serverTypePath, zkChildListener);
        LogUtils.info(String.format("subscribe: path=%s, info=%s", toNodePath(dataMap, ZkNodeType.AVAILABLE_SERVER), currentChilds), logger);
        notify(dataMap, notifyListener, nodeChildsToUrls(serverTypePath, currentChilds));
    }

    @Override
    public void unsubscribe(DataMap dataMap, NotifyListener notifyListener) {
        final String key = toNodePath(dataMap, ZkNodeType.CLIENT);
        Map<NotifyListener, IZkChildListener> childChangeListeners = urlListeners.get(key);
        if (childChangeListeners != null) {
            IZkChildListener zkChildListener = childChangeListeners.get(notifyListener);
            if (zkChildListener != null) {
                zkClient.unsubscribeChildChanges(toNodeTypePath(dataMap, ZkNodeType.CLIENT), zkChildListener);
                childChangeListeners.remove(notifyListener);
            }
        }
    }

    protected void notify(DataMap dataMap, NotifyListener listener, List<DataMap> dataMapList) {
        if (listener == null) {
            return;
        }
        listener.notify(dataMap, dataMapList);
    }

    private List<DataMap> nodeChildsToUrls(String parentPath, List<String> currentChilds) {
        List<DataMap> serviceList = new ArrayList<DataMap>();
        if (currentChilds == null) {
            return serviceList;
        }
        for (String node : currentChilds) {
            String nodePath = parentPath + PATH_SEPARATOR + node;
            String data = zkClient.readData(nodePath, true);
            if (logger.isDebugEnabled()) {
                logger.debug("serviceInfo [" + data + "]");
            }
            DataMap dataMap = DataUtils.json2dataMap(data);
            serviceList.add(dataMap);
        }
        return serviceList;
    }

    private void removeNode(DataMap dataMap, ZkNodeType nodeType) {
        String nodePath = toNodePath(dataMap, nodeType);
        if (zkClient.exists(nodePath)) {
            zkClient.delete(nodePath);
        }
    }

    private void createNode(DataMap dataMap, ZkNodeType nodeType) {
        String nodeTypePath = toNodeTypePath(dataMap, nodeType);
        if (!zkClient.exists(nodeTypePath)) {
            zkClient.createPersistent(nodeTypePath, true);
        }
        zkClient.createEphemeral(toNodePath(dataMap, nodeType), dataMap.getString(KEY_DATA));
    }
    private String toNodePath(DataMap dataMap, ZkNodeType nodeType) {
        return toNodeTypePath(dataMap, nodeType) + PATH_SEPARATOR + dataMap.getString(KEY_HOST) + ":" + dataMap.getString(KEY_PORT);
    }

    private String toServicePath(DataMap dataMap) {
        return REGISTRY_NAMESPACE + PATH_SEPARATOR + dataMap.getString(KEY_GROUP) + PATH_SEPARATOR + dataMap.getString(KEY_PATH);
    }

    private String toNodeTypePath(DataMap dataMap, ZkNodeType nodeType) {
        String type = "error";
        if (nodeType == ZkNodeType.AVAILABLE_SERVER) {
            type = "server";
        } else if (nodeType == ZkNodeType.UNAVAILABLE_SERVER) {
            type = "unavailableServer";
        } else if (nodeType == ZkNodeType.CLIENT) {
            type = "client";
        } else {
            LogUtils.error(String.format("Failed to get nodeTypePath, type: %s", nodeType.toString()), logger);
        }
        return toServicePath(dataMap) + PATH_SEPARATOR + type;
    }

    @Override
    public void destroy() throws Exception {
        if (zkClient != null) {
            List<DataMap> registryList = getRegisteredServices();
            LogUtils.info("destory service size [" + registryList.size() + "]", logger);
            for (DataMap dataMap : registryList) {
                unavailable(dataMap);
            }
            zkClient.close();
        }
    }

    @Override
    public List<String> getGroups() {
        return zkClient.getChildren(REGISTRY_NAMESPACE);
    }

    @Override
    public List<String> getAddress(String group, String type) {
        DataMap dataMap = DataMap.Builder.create();
        dataMap.put(KEY_GROUP, group).put(KEY_PATH, Service.class.getName());
        String path = toServicePath(dataMap) + PATH_SEPARATOR + type;
        return zkClient.getChildren(path);
    }

    @Override
    public DataMap getServices(String group, String type, String address) {
        DataMap dataMap = DataMap.Builder.create();
        dataMap.put(KEY_GROUP, group).put(KEY_PATH, Service.class.getName());
        String nodePath = toServicePath(dataMap) + PATH_SEPARATOR + type + PATH_SEPARATOR + address;
        String data = zkClient.readData(nodePath, true);
        if (logger.isDebugEnabled()) {
            logger.debug("serviceInfo [" + data + "]");
        }
        return DataUtils.json2dataMap(data);
    }

    enum ZkNodeType {
        AVAILABLE_SERVER,
        UNAVAILABLE_SERVER,
        CLIENT;
    }
}
