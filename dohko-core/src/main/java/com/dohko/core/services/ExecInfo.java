package com.dohko.core.services;

import com.dohko.core.base.DataMap;

import java.util.Map;

/**
 * Created by xiangbin on 2016/6/15.
 */
public interface ExecInfo {

    void serviceInfo(String service, DataMap dataMap);

    void registryInfo(DataMap dataMap);

    Map<String, String> getCoreServices();
}
