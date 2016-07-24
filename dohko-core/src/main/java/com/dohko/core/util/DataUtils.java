package com.dohko.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dohko.core.base.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataUtils {

	private static Logger logger = LoggerFactory.getLogger(DataUtils.class);
	private static Pattern DIGIT_PATTERN = Pattern.compile("\\d+");
	private static Pattern NUMBER_PATTERN = Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?");
	
	public static boolean isDigit(String obj) {
		Matcher matcher = DIGIT_PATTERN.matcher(obj);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isDecimal(String obj) {
		if (NUMBER_PATTERN.matcher(obj).matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static String dataMap2json(DataMap dataMap) {
		Map<String, Object> map = dataMap.toMap();
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toJSONString();
	}

	public static DataMap map2DataMap(Map<String, Object> map) {
		DataMap dataMap = DataMap.Builder.create();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			Object obj = map.get(key);
			if (obj == null) {
				LogUtils.error("key [" + key + "] not put null", logger);
			} else if (obj instanceof String) {
				dataMap.put(key, (String)obj);
			} else if (obj instanceof Integer) {
				dataMap.put(key, (Integer)obj);
			} else if (obj instanceof BigDecimal) {
				dataMap.put(key, (BigDecimal)obj);
			} else if (obj instanceof Long) {
				dataMap.put(key, (Long)obj);
			} else if (obj instanceof Float) {
				dataMap.put(key, (Float)obj);
			} else if (obj instanceof Double) {
				dataMap.put(key, (Double) obj);
			} else if (obj instanceof BigInteger) {
				dataMap.put(key, (BigInteger) obj);
			} else if (obj instanceof Boolean) {
				dataMap.put(key, (Boolean) obj);
			} else {
				LogUtils.error("not support type [" + obj.getClass() +" ]", logger);
			}
		}
		return dataMap;
	}

	public static DataMap json2dataMap(String jsondata) {
		DataMap dataMap = DataMap.Builder.create();
		JSONObject jsonObj = JSON.parseObject(jsondata);
		Set<String> keySet = jsonObj.keySet();
		for (String key : keySet) {
			Object obj = jsonObj.get(key);
			if (obj instanceof String) {
				dataMap.put(key, (String)obj);
			} else if (obj instanceof Integer) {
				dataMap.put(key, (Integer)obj);
			} else if (obj instanceof BigDecimal) {
				dataMap.put(key, (BigDecimal)obj);
			} else if (obj instanceof Long) {
				dataMap.put(key, (Long)obj);
			} else if (obj instanceof Float) {
				dataMap.put(key, (Float)obj);
			} else if (obj instanceof Double) {
				dataMap.put(key, (Double) obj);
			} else if (obj instanceof BigInteger) {
				dataMap.put(key, (BigInteger) obj);
			} else if (obj instanceof Boolean) {
				dataMap.put(key, (Boolean) obj);
			} else {
				LogUtils.error("not support type [" + obj.getClass() +" ]", logger);
			}
		}
		return dataMap;
	}

	
	public static String response2json(Response response) {
		Map<String, Object> map = response.toMap();
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toJSONString();
	}

	public static String request2json(Request request) {
		Map<String, Object> map = request.toMap();
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toJSONString();
	}

	public static DataMap toDataMap(Response response) {
		if (!response.success()) {
			return ResultInfo.Builder.createDataMap(response.getReturnCode(), response.getReturnMessage());
		}
		return response.getDataMap();
	}

	public static String resultInfo2json(ResultInfo resultInfo) {
		Map<String, Object> map = resultInfo2map(resultInfo);
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toJSONString();
	}

	private static Map<String, Object> resultInfo2map(ResultInfo resultInfo) {
		if (resultInfo instanceof DataMap) {
			return ((DataMap)resultInfo).toMap();
		} else{
			Map<String, Object> map = new HashMap<>();
			map.put("resultCode", resultInfo.resultCode());
			map.put("resultMessage", resultInfo.resultMessage());
			return map;
		}
	}

	public static Object toObject(DataMap dataMap, String key) {
		DataMap.ValueType type = dataMap.getType(key);
		if (type == DataMap.ValueType.BIG_DECIMAL) {
			return dataMap.getBigDecimal(key);
		} else if (type == DataMap.ValueType.FLOAT) {
			return dataMap.getFloat(key);
		} else if (type == DataMap.ValueType.STRING) {
			return dataMap.getString(key);
		} else if (type == DataMap.ValueType.BOOL) {
			return dataMap.getBoolean(key);
		} else if (type == DataMap.ValueType.INT) {
			return dataMap.getInteger(key);
		} else if (type == DataMap.ValueType.LONG) {
			return dataMap.getLong(key);
		} else if (type == DataMap.ValueType.BIG_INTEGER) {
			return dataMap.getBigInteger(key);
		} else if (type == DataMap.ValueType.DATA_MAP) {
			return dataMap.getDataMap(key);
		} else if (type == DataMap.ValueType.DATA_LIST) {
			return dataMap.getDataList(key);
		}
		return null;
	}

	public static void putObject(DataMap dataMap, String key, Object value) {
		if (value instanceof BigDecimal) {
			dataMap.put(key, (BigDecimal)value);
		} else if (value instanceof Float) {
			dataMap.put(key, (Float) value);
		} else if (value instanceof String) {
			dataMap.put(key, (String) value);
		} else if (value instanceof Boolean) {
			dataMap.put(key, (Boolean) value);
		} else if (value instanceof Integer) {
			dataMap.put(key, (Integer) value);
		} else if (value instanceof Long) {
			dataMap.put(key, (Long) value);
		} else if (value instanceof BigInteger) {
			dataMap.put(key, (BigInteger) value);
		} else if (value instanceof DataMap) {
			dataMap.put(key, (DataMap)value);
		} else if (value instanceof DataList) {
			dataMap.put(key, (DataList)value);
		} else {
			logger.error("not support key [" + key + "] value [" + value + "]");
		}
	}
}
