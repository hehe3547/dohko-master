package com.dohko.core.app.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dohko.core.app.bean.DataBean;
import com.dohko.core.app.bean.ListBean;
import com.dohko.core.app.bean.MapBean;
import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.LogUtils;
import com.dohko.core.util.OgnlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/29.
 */
public class MapFilterSupport extends ValidatorSupport {

    private Logger logger = LoggerFactory.getLogger(MapFilterSupport.class);

    public DataMap doFilterMap(DataMap paramsMap, MapBean mapBean, Map<String, Object> ognl) {
        DataMap retMap = DataMap.Builder.create();
        if (mapBean.isOriginal()) {
            retMap.putAll(paramsMap);
        }
        for (DataBean dataBean : mapBean.getDatas()) {
            parseDataValue(retMap, dataBean, ognl);
        }
        return retMap;
    }

    private DataMap parseDataValue(DataMap dataMap, DataBean dataBean, Map<String, Object> ognl) {
        String key = dataBean.getKey();
        try {
            Object value = dataBean.getValue();
            if (value == null && dataBean.getSource() != null && dataMap.contains(dataBean.getSource())) {
                String source = dataBean.getSource();
                value = DataUtils.toObject(dataMap, source);
            }
            if (value == null && dataBean.getExpression() != null) {
                value = OgnlUtils.toValue(dataBean.getExpression(), ognl, dataBean);
            }
            if (value == null && dataBean.getMapBean() != null) {
                value = doFilterMap(dataMap.getDataMap(key), dataBean.getMapBean(), ognl);
            }
            if (value == null && dataBean.getListBean() != null) {
                value = doFilterList(dataMap.getDataList(key), dataBean.getListBean(), ognl);
            }
            if (value == null) {
                return dataMap;
            }
            if ( dataBean.getType() != null) {
                if (DataBean.Type.INT.equals(dataBean.getType())) {
                    if (value instanceof Integer) {
                        dataMap.put(key, (Integer)value);
                    } else {
                        dataMap.put(key, Integer.parseInt(value.toString()));
                    }
                } else if (DataBean.Type.DATE.equals(dataBean.getType())) {
                    String pattern = dataBean.getPattern() == null ? "yyyyMMddHHmmss" : dataBean.getPattern();
                    Date date = new SimpleDateFormat(dataBean.getSourcePattern()).parse(value.toString());
                    dataMap.put(key, new SimpleDateFormat(pattern).format(date));
                } else if (DataBean.Type.DECIMAL.equals(dataBean.getType())) {
                    String pattern = dataBean.getPattern() == null ? "###.##" : dataBean.getPattern();
                    if (value instanceof BigDecimal) {
                        dataMap.put(key, new DecimalFormat(pattern).format((BigDecimal)value));
                    } else {
                        dataMap.put(key, new DecimalFormat(pattern).format(new BigDecimal(value.toString())));
                    }
                } else if (DataBean.Type.JSON == dataBean.getType()) {
                    JSON json = JSON.parseObject(value.toString());
                    if (json instanceof JSONArray) {
                        dataMap.put(key, parseJson((JSONArray)json));
                    } else if (json instanceof JSONObject) {
                        dataMap.put(key, parseJson((JSONObject)json));
                    }
                }
            } else {
                dataMap.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.error("parse key [" + key + "] value error, dataBean [" + dataBean + "]", logger);
        }
        return dataMap;
    }

    private DataList parseJson(JSONArray jsonArray) {
        DataList dataList = DataList.Builder.create();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            dataList.add(parseJson(jsonObject));
        }
        return dataList;
    }
    private DataMap parseJson(JSONObject jsonObject) {
        DataMap dataMap = DataMap.Builder.create();
        for (String key : jsonObject.keySet()) {
            Object object = jsonObject.get(key);
            if (object instanceof JSONObject) {
                dataMap.put(key, parseJson((JSONObject)object));
            } else if (object instanceof JSONArray) {
                dataMap.put(key, parseJson((JSONArray)object));
            } else {
                dataMap.put(key, object);
            }
        }
        return dataMap;
    }

    private DataList doFilterList(DataList paramsList, ListBean listBean, Map<String, Object> ognl) {
        DataList dataList = DataList.Builder.create();
        int size = paramsList == null ? listBean.getCount() : paramsList.size();
        for (int i = 0; i < size; i++) {
            DataMap dataMap = paramsList == null ? DataMap.Builder.create() : paramsList.get(i);
            DataMap retMap = DataMap.Builder.create();
            if (listBean.isOriginal()) {
                retMap.putAll(dataMap);
            }
            ognl.put(listBean.getIndex(), i);
            for (DataBean dataBean : listBean.getDatas()) {
                parseDataValue(retMap, dataBean, ognl);
            }
            dataList.add(retMap);
        }
        return dataList;
    }

}
