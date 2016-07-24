package com.dohko.core.base;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/27.
 */
public interface DataList extends ResultInfo {

    int size();

    List<DataMap> toList();

    List<Map<String, Object>> toListMap();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<DataMap> iterator();

    DataMap[] toArray(DataMap[] a);

    DataList add(DataMap dataMap);

    boolean remove(Object o);

    DataList addAll(List<? extends DataMap> c);

    DataList addValues(String dataKey, List<String> values);

    List<String> toValues(String dataKey);

    DataList addIntegerValues(String dataKey, List<Integer> values);

    List<Integer> toIntegerValues(String dataKey);

    DataList addBigDecimalValues(String dataKey, List<BigDecimal> values);

    List<BigDecimal> toBigDecimalValues(String dataKey);

    DataList addAll(int index, List<? extends DataMap> c);

    void clear();

    DataMap get(int index);

    DataMap set(int index, DataMap element);

    DataList add(int index, DataMap element);

    DataMap remove(int index);

    int indexOf(Object o);

    final class Builder {
        public static DataList create() {
            return new DataListImpl();
        }

        public static DataList create(List<DataMap> dataMapList) {
            DataList dataList = create().addAll(dataMapList);
            return dataList;
        }
    }
}
