package com.dohko.core.base;

import com.dohko.core.PlatResultInfo;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by xiangbin on 2016/6/27.
 */
public class DataListImpl extends ResultInfoImpl implements DataList {

    private final List<DataMap> list = new ArrayList<>();
    private String KEY_STRING = "_string_";
    private String KEY_INTEGER = "_integer_";
    private String KEY_BIGDECIMAL = "_bigdecimal_";

    DataListImpl() {
        super(PlatResultInfo.SUCCESS.resultCode(), PlatResultInfo.SUCCESS.resultMessage());
    }
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<DataMap> toList() {
        return list;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<DataMap> iterator() {
        return list.iterator();
    }

    @Override
    public DataMap[] toArray(DataMap[] a) {
        return list.toArray(a);
    }

    @Override
    public DataList add(DataMap dataMap) {
        list.add(dataMap);
        return this;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public DataList addAll(List<? extends DataMap> c) {
        list.addAll(c);
        return this;
    }

    @Override
    public DataList addValues(String dataKey, List<String> values) {
        for (String value : values) {
            list.add(DataMap.Builder.create().put(dataKey, value));
        }
        return this;
    }

    @Override
    public List<String> toValues(String dataKey) {
        List<String> retList = new ArrayList<>();
        for (DataMap dataMap : list) {
            if (dataMap.contains(dataKey)) {
                retList.add(dataMap.getString(dataKey));
            }
        }
        return retList;
    }

    @Override
    public DataList addIntegerValues(String dataKey, List<Integer> values) {
        for (Integer value : values) {
            list.add(DataMap.Builder.create().put(dataKey, value));
        }
        return this;
    }

    @Override
    public List<Integer> toIntegerValues(String dataKey) {
        List<Integer> retList = new ArrayList<>();
        for (DataMap dataMap : list) {
            if (dataMap.contains(dataKey)) {
                retList.add(dataMap.getInteger(dataKey));
            }
        }
        return retList;
    }

    @Override
    public DataList addBigDecimalValues(String dataKey, List<BigDecimal> values) {
        for (BigDecimal value : values) {
            list.add(DataMap.Builder.create().put(dataKey, value));
        }
        return this;
    }

    @Override
    public List<BigDecimal> toBigDecimalValues(String dataKey) {
        List<BigDecimal> retList = new ArrayList<>();
        for (DataMap dataMap : list) {
            if (dataMap.contains(dataKey)) {
                retList.add(dataMap.getBigDecimal(dataKey));
            }
        }
        return retList;
    }

    @Override
    public DataList addAll(int index, List<? extends DataMap> c) {
        list.addAll(index, c);
        return this;
    }


    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public DataMap get(int index) {
        return list.get(index);
    }

    @Override
    public DataMap set(int index, DataMap element) {
        return list.set(index, element);
    }

    @Override
    public DataList add(int index, DataMap element) {
        list.add(index, element);
        return this;
    }

    @Override
    public DataMap remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public List<Map<String, Object>> toListMap() {
        List<Map<String, Object>> retList = new ArrayList<>();
        for (DataMap dataMap : list) {
            Map<String, Object> retMap = dataMap.toMap();
            retList.add(retMap);
        }
        return retList;
    }
}
