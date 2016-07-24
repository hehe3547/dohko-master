package com.dohko.core.app;

import com.alibaba.fastjson.JSONObject;
import com.dohko.core.app.bean.DataBean;
import com.dohko.core.app.bean.ListBean;
import com.dohko.core.app.bean.MapBean;
import com.dohko.core.app.support.FilterSupport;
import com.dohko.core.base.DataMap;
import com.dohko.core.util.DataUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/27.
 */

public class FilterSupportTest {
    @Test
    public void doFilterResData() {
        List<DataBean> dataBeanList = new ArrayList<>();
        DataBean dataBean1 = new DataBean("key1");
        dataBean1.setValue("value1");
        dataBeanList.add(dataBean1);


        List<DataBean> mapList = new ArrayList<>();
        DataBean mapDataBean1 = new DataBean("map-key1");
        mapDataBean1.setValue("map-value1");
        mapList.add(mapDataBean1);
        DataBean mapDataBean2 = new DataBean("map-key2");
        mapDataBean2.setValue("map-value2");
        mapList.add(mapDataBean2);
        MapBean mapBean1 = new MapBean(mapList);

        DataBean dataBean2 = new DataBean("map");
        dataBean2.setMapBean(mapBean1);

        List<DataBean> listList = new ArrayList<>();
        DataBean listDataBean1 = new DataBean("list-key1");
        listDataBean1.setValue("list-value1");
        listList.add(listDataBean1);
        DataBean listDataBean2 = new DataBean("list-key2");
        listDataBean2.setExpression("#c");
        listList.add(listDataBean2);

        ListBean listBean1 = new ListBean(listList);
        listBean1.setIndex("c");
        listBean1.setCount(2);

        DataBean dataBean3 = new DataBean("list");
        dataBean3.setListBean(listBean1);

        MapBean mapBean = new MapBean();
        mapBean.addDataBean(dataBean1);
        mapBean.addDataBean(dataBean2);
        mapBean.addDataBean(dataBean3);
        DataBean dataBean4 = new DataBean("jsonMapKey");
        JSONObject jsonMap = new JSONObject();
        jsonMap.put("jsonMap1", "jsonMapValue1");
        jsonMap.put("jsonMap2", "jsonMapValue2");
        dataBean4.setType(DataBean.Type.JSON);
        dataBean4.setValue(jsonMap.toJSONString());
        mapBean.addDataBean(dataBean4);

        DataBean dataBean5 = new DataBean("jsonListKey");
        JSONObject jsonList = new JSONObject();
        jsonList.put("jsonList1", "jsonListValue1");
        jsonList.put("jsonList2", "jsonListValue2");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> t = new HashMap<>();
            t.put("key" + i, "value" + i);
            list.add(t);
        }
        jsonList.put("jsonList3", list);
        dataBean5.setType(DataBean.Type.JSON);
        dataBean5.setValue(jsonList.toJSONString());
        mapBean.addDataBean(dataBean5);

        FilterSupport support = new FilterSupport();
        DataMap dataMap = support.doFilterMap(DataMap.Builder.create(), mapBean, new HashMap<String, Object>());
        System.out.print("dataMap [" + DataUtils.dataMap2json(dataMap) + "]");
    }
}
