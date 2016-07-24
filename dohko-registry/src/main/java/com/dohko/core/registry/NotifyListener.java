package com.dohko.core.registry;

import com.dohko.core.base.DataMap;

import java.util.List;

public interface NotifyListener {

    void notify(DataMap dataMap, List<DataMap> serviceList);
}
