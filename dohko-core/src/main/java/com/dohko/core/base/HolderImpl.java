package com.dohko.core.base;

import java.util.Map;

/**
 * Created by xiangbin on 2016/6/13.
 */
public class HolderImpl implements Holder {

    private final DataMap dataMap;

    HolderImpl(DataMap dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String getValue(String key) {
        return dataMap.getString(key);
    }

    @Override
    public String getValue(Key key) {
        return getValue(key.getKey());
    }

    @Override
    public DataMap getDataMap() {
        return dataMap;
    }

    @Override
    public Map<String, Object> toMap() {
        return dataMap.toMap();
    }
}
