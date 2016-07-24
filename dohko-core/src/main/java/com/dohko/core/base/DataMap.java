package com.dohko.core.base;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

/**
 * 等同于Map,
 * 用于重新定义Map,key值必须为字符串
 * value值只能包括string, int, long, double, boolean 绝对不能包括Map和List
 * @author xiangbin
 *
 */
public interface DataMap extends ResultInfo {

	String DEFAULT_DATA_LIST = "_DEFAULT_DATA_LIST_";
	String PAGE_NO = "pageNo";
	String PAGE_SIZE = "pageSize";

	DataMap put(String key, String value);
	
	DataMap put(String key, Integer value);
	
	DataMap put(String key, Long value);
	
	DataMap put(String key, Double value);
	
	DataMap put(String key, Float value);
	
	DataMap put(String key, BigDecimal value);
	
	DataMap put(String key, BigInteger value);
	
	DataMap put(String key, Boolean value);

	DataMap put(String key, DataMap dataMap);

	DataMap getDataMap(String key);

	DataMap put(String key, DataList dataList);

	DataMap put(String key, Object object);

	DataList getDataList(String key);
	//返回默认key值的DataList
	DataList getDataList();
	
	String getString(String key);
	
	String getString(String key, String defaultValue);
	
	Integer getInteger(String key);
	
	Integer getInteger(String key, Integer defaultValue);
	
	int getIntValue(String key);
	
	int getIntValue(String key, int defaultValue);
	
	Long getLong(String key);
	
	Long getLong(String key, Long defaultValue);
	
	long getLongValue(String key);
	
	long getLongValue(String key, long defaultValue);
	
	Double getDouble(String key);
	
	Double getDouble(String key, Double defaultValue);
	
	double getDoubleValue(String key);
	
	double getDoubleValue(String key, double defaultValue);
	
	Float getFloat(String key);
	
	Float getFloat(String key, Float defaultValue);
	
	float getFloatValue(String key);
	
	float getFloatValue(String key, float defaultValue);
	
	Boolean getBoolean(String key);
	
	Boolean getBoolean(String key, Boolean defaultValue);
	
	boolean getBooleanValue(String key);
	
	boolean getBooleanValue(String key, boolean defaultValue);
	
	BigDecimal getBigDecimal(String key);
	
	BigDecimal getBigDecimal(String key, BigDecimal defaultValue);
	
	BigInteger getBigInteger(String key);
	
	BigInteger getBigInteger(String key, BigInteger defaultValue);

	Object getObject(String key);

	boolean isEmpty();
	
	DataMap putAll(DataMap dataMap);
	
	DataMap putAll(DataMap dataMap, boolean beforeClear);

	DataMap putAll(Map<String, Object> map);
	
	void clear();
	
	Set<String> keySet();
	
	boolean contains(String key);
	
	int size();
	
	void remove(String key);
	
	ValueType getType(String key);

	Map<String, Object> toMap();
	
	enum ValueType {
		DOUBLE(1, "getDouble"),
	    FLOAT(2, "getFloat"),
	    STRING(3, "getString"),
	    BOOL(4, "getBoolean"),
	    INT(5, "getInteger"),
	    LONG(6, "getLong"),
	    BIG_DECIMAL(7, "getBigDecimal"),
	    BIG_INTEGER(8, "getBigInteger"),
		DATA_MAP(9, "getDataMap"),
		DATA_LIST(10, "getDataList"),
		NOT_SUPPORT(11, "double");

		private int number = 0;
		private String value;
		ValueType(int number, String value) {
			this.number = number;
	      	this.value = value;
	    }
	    public int getNumber() {
	      return this.number;
	    }

		public String getValue() {
			return value;
		}
	}
	
	final class Builder {
		
		public static DataMap create() {
			return new DataMapImpl();
		}

		public static DataMap create(Map<String, Object> map) {
			DataMap dataMap = create();
			dataMap.putAll(map);
			return dataMap;
		}
	}
}
