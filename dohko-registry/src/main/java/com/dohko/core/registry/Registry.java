package com.dohko.core.registry;

import com.dohko.core.base.DataMap;

import java.util.List;

/**
 * Created by xiangbin on 2016/6/14.
 */
public interface Registry {
    //分组
    String KEY_GROUP = "group";
    String KEY_PORT = "port";
    //地址
    String KEY_HOST = "host";
    //服务名称,
    String KEY_PATH = "path";
    String KEY_DATA = "data";
    String KEY_SERVICES = "service";
    String KEY_SIZE = "size";

    void subscribe(DataMap dataMap, NotifyListener listener);

    void unsubscribe(DataMap dataMap, NotifyListener listener);

    List<DataMap> discover(DataMap dataMap);

    void register(DataMap dataMap);

    void unregister(DataMap dataMap);

    void available(DataMap dataMap);

    void unavailable(DataMap dataMap);

    List<DataMap> getRegisteredServices();

}
