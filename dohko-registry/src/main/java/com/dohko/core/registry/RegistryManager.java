package com.dohko.core.registry;

import com.dohko.core.base.DataMap;

import java.util.List;

/**
 * Created by xiangbin on 2016/7/3.
 */
public interface RegistryManager {

    List<String> getGroups();

    List<String> getAddress(String group, String type);

    DataMap getServices(String group, String type, String address);
}
