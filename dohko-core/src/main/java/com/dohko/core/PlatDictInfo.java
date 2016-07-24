package com.dohko.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/16.
 */
public final class PlatDictInfo {

    private static final String[] dicts = {"recrds", "shopID", "shopName", "groupID", "groupName", "orderID", "orderKey"};
    private static final int size = dicts.length;
    private static Map<String, Integer> dictCache = new HashMap<>(size);

    static {
        for (int i = 0; i < dicts.length; i++) {
            dictCache.put(dicts[i], i);
        }
    }

    public static int size() {
        return size;
    }

    public static int indexOf(String key) {
        Integer index = dictCache.get(key);
        if (index == null) {
            return -1;
        }
        return index.intValue();
    }

    public static String value(int index) {
        return dicts[index];
    }
}
