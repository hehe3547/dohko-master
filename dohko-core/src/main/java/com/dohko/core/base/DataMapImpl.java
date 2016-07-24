package com.dohko.core.base;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataMapImpl extends ResultInfoImpl implements DataMap {

	private Logger logger = LoggerFactory.getLogger(DataMapImpl.class);

	private final Map<String, MapValue> datas = new HashMap<>();
	
	DataMapImpl() {
		super(PlatResultInfo.SUCCESS.resultCode(), PlatResultInfo.SUCCESS.resultMessage());
	}

	DataMapImpl(String resultCode, String resultMessage) {
		super(resultCode, resultMessage);
	}
	
	@Override
	public int size() {
		return datas.size();
	}

	@Override
	public boolean isEmpty() {
		return this.datas.isEmpty();
	}

	@Override
	public boolean contains(String key) {
		return this.datas.containsKey(key);
	}

	@Override
	public void remove(String key) {
		datas.remove(key);
	}

	@Override
	public DataMap putAll(DataMap dataMap) {
		return putAll(dataMap, false);
	}

	@Override
	public DataMap putAll(DataMap dataMap, boolean beforeClear) {
		if (beforeClear) {
			this.datas.clear();
		}
		if (!(dataMap instanceof DataMapImpl)) {
			LogUtils.error("not support datamap type [" + dataMap.getClass().getName() + "]", logger);
			return this;
		}
		DataMapImpl impl = (DataMapImpl)dataMap;
		Set<String> keySet = impl.keySet();
		for (String key : keySet) {
			ValueType valueType = dataMap.getType(key);
			switch (valueType) {
				case STRING:
					put(key, dataMap.getString(key));
					break;
				case DOUBLE:
					put(key, dataMap.getDouble(key));
					break;
				case FLOAT:
					put(key, dataMap.getFloat(key));
					break;
				case BOOL:
					put(key, dataMap.getBoolean(key));
					break;
				case INT:
					put(key, dataMap.getInteger(key));
					break;
				case LONG:
					put(key, dataMap.getLong(key));
					break;
				case BIG_DECIMAL:
					put(key, dataMap.getBigDecimal(key));
					break;
				case BIG_INTEGER:
					put(key, dataMap.getBigInteger(key));
					break;
				case DATA_LIST:
					put(key, dataMap.getDataList(key));
					break;
				case DATA_MAP:
					put(key, dataMap.getDataMap(key));
					break;
				case NOT_SUPPORT:
			}
		}
		return this;
	}
	@Override
	public void clear() {
		this.datas.clear();
	}

	@Override
	public Set<String> keySet() {
		return this.datas.keySet();
	}

	@Override
	public DataMap put(String key, String value) {
		this.datas.put(key, new MapValue(value, ValueType.STRING));
		return this;
	}

	@Override
	public DataMap put(String key, Integer value) {
		this.datas.put(key, new MapValue(value, ValueType.INT));
		return this;
	}

	@Override
	public DataMap put(String key, Long value) {
		this.datas.put(key, new MapValue(value, ValueType.LONG));
		return this;
	}

	@Override
	public DataMap put(String key, Double value) {
		this.datas.put(key, new MapValue(value, ValueType.DOUBLE));
		return this;
	}

	@Override
	public DataMap put(String key, Float value) {
		this.datas.put(key, new MapValue(value, ValueType.FLOAT));
		return this;
	}

	@Override
	public DataMap put(String key, BigDecimal value) {
		this.datas.put(key, new MapValue(value, ValueType.BIG_DECIMAL));
		return this;
	}

	@Override
	public DataMap put(String key, BigInteger value) {
		this.datas.put(key, new MapValue(value, ValueType.BIG_INTEGER));
		return this;
	}

	@Override
	public DataMap put(String key, Boolean value) {
		this.datas.put(key, new MapValue(value, ValueType.BOOL));
		return this;
	}

	@Override
	public DataMap put(String key, DataMap dataMap) {
		this.datas.put(key, new MapValue(dataMap, ValueType.DATA_MAP));
		return this;
	}

	@Override
	public DataMap getDataMap(String key) {
		if (ValueType.DATA_MAP == getType(key)) {
			return (DataMap)this.datas.get(key).getValue();
		}
		return null;
	}

	@Override
	public DataMap put(String key, DataList dataMapList) {
		this.datas.put(key, new MapValue(dataMapList, ValueType.DATA_LIST));
		return this;
	}

	@Override
	public DataMap putAll(Map<String, Object> map) {
		for (String key : map.keySet()) {
			put(key, map.get(key));
		}
		return this;
	}

	@Override
	public Object getObject(String key) {
		return this.datas.get(key).getValue();
	}

	@Override
	public DataMap put(String key, Object value) {
		if (key == null || value == null) {
			logger.error("not support key [" + key + "] value [" + value + "] is null");
			return this;
		}
		if (value instanceof BigDecimal) {
			put(key, (BigDecimal)value);
		} else if (value instanceof Float) {
			put(key, (Float) value);
		} else if (value instanceof String) {
			put(key, (String) value);
		} else if (value instanceof Boolean) {
			put(key, (Boolean) value);
		} else if (value instanceof Integer) {
			put(key, (Integer) value);
		} else if (value instanceof Long) {
			put(key, (Long) value);
		} else if (value instanceof BigInteger) {
			put(key, (BigInteger) value);
		} else if (value instanceof DataMap) {
			put(key, (DataMap)value);
		} else if (value instanceof DataList) {
			put(key, (DataList)value);
		} else {
			logger.error("not support key [" + key + "] value [" + value + "]");
		}
		return this;
	}

	@Override
	public DataList getDataList(String key) {
		if (ValueType.DATA_LIST == getType(key)) {
			return (DataList)this.datas.get(key).getValue();
		}
		return null;
	}

	@Override
	public DataList getDataList() {
		DataList dataList = null;
		for (MapValue mapValue : this.datas.values()) {
			if (mapValue.getType() == ValueType.DATA_LIST) {
				if (dataList == null) {
					dataList = (DataList) mapValue.getValue();
				} else {
					//TODO 异常
				}
			}
		}
		return dataList;
	}

	@Override
	public String getString(String key) {
		return getString(key, null);
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.STRING)) {
			return (String)obj.getValue();
		} else {
			return obj.getValue().toString();
		}
	}

	@Override
	public Integer getInteger(String key) {
		return getInteger(key, null);
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.INT)) {
			return (Integer)obj.getValue();
		} else {
			if (isDigit(obj)) {
				return new Integer(obj.getValue().toString());
			} else {
				return null;
			}
		}
	}

	@Override
	public int getIntValue(String key) {
		return getInteger(key, 0);
	}

	@Override
	public int getIntValue(String key, int defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.INT)) {
			return ((Integer)obj.getValue()).intValue();
		} else {
			if (isDigit(obj)) {
				return Integer.parseInt(obj.getValue().toString());
			}
			return defaultValue;
		}
	}

	@Override
	public Long getLong(String key) {
		return getLong(key, null);
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.LONG)) {
			return ((Long)obj.getValue());
		} else {
			if (isDigit(obj)) {
				return new Long(obj.getValue().toString());
			}
			return defaultValue;
		}
	}
	
	private boolean isDigit(MapValue obj) {
		return DataUtils.isDigit(obj.getValue().toString());
	}

	@Override
	public long getLongValue(String key) {
		return getLongValue(key, 0);
	}

	@Override
	public long getLongValue(String key, long defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.LONG)) {
			return ((Long)obj.getValue()).longValue();
		} else {
			if (isDigit(obj)) {
				return Long.parseLong(obj.getValue().toString());
			}
			return defaultValue;
		}
	}

	@Override
	public Double getDouble(String key) {
		return getDouble(key, null);
	}

	@Override
	public Double getDouble(String key, Double defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.DOUBLE)) {
			return ((Double)obj.getValue());
		} else {
			if (isDecimal(obj)) {
				return new Double(obj.getValue().toString());
			} else {
				return defaultValue;
			}
			
		}
	}

	@Override
	public double getDoubleValue(String key) {
		return getDoubleValue(key, 0);
	}

	private boolean isDecimal(MapValue obj) {
		return DataUtils.isDecimal(obj.getValue().toString());
	}
	
	@Override
	public double getDoubleValue(String key, double defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.DOUBLE)) {
			return ((Double)obj.getValue()).doubleValue();
		} else {
			if (isDecimal(obj)) {
				return Double.parseDouble(obj.getValue().toString());
			} else {
				return defaultValue;
			}
		}
	}

	@Override
	public Float getFloat(String key) {
		return getFloat(key, null);
	}

	@Override
	public Float getFloat(String key, Float defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.FLOAT)) {
			return ((Float)obj.getValue());
		} else {
			if (isDecimal(obj)) {
				return new Float(obj.getValue().toString());
			} else {
				return defaultValue;
			}
		}
	}

	@Override
	public float getFloatValue(String key) {
		return getFloatValue(key, 0);
	}

	@Override
	public float getFloatValue(String key, float defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.FLOAT)) {
			return ((Float)obj.getValue()).floatValue();
		} else {
			if (isDecimal(obj)) {
				return Float.parseFloat(obj.getValue().toString());
			} else {
				return defaultValue;
			}
		}
	}

	@Override
	public Boolean getBoolean(String key) {
		return getBoolean(key, Boolean.FALSE);
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.BOOL)) {
			return ((Boolean)obj.getValue());
		} else {
			return new Boolean(obj.getValue().toString());
		}
	}

	@Override
	public boolean getBooleanValue(String key) {
		return getBooleanValue(key, false);
	}

	@Override
	public boolean getBooleanValue(String key, boolean defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.BOOL)) {
			return ((Boolean)obj.getValue()).booleanValue();
		} else {
			return Boolean.parseBoolean(obj.getValue().toString());
		}
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		return getBigDecimal(key, null);
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.BIG_DECIMAL)) {
			return ((BigDecimal)obj.getValue());
		} else {
			if (isDecimal(obj)) {
				return new BigDecimal(obj.getValue().toString());
			} else {
				return defaultValue;
			}
		}
	}

	@Override
	public BigInteger getBigInteger(String key) {
		return getBigInteger(key, null);
	}

	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		if (!contains(key)) {
			return defaultValue;
		}
		MapValue obj = this.datas.get(key);
		if (obj.getType().equals(ValueType.BIG_INTEGER)) {
			return ((BigInteger)obj.getValue());
		} else {
			if (isDigit(obj)) {
				return new BigInteger(obj.getValue().toString());
			} else {
				return defaultValue;
			}
		}
	}

	@Override
	public ValueType getType(String key) {
		if (!this.datas.containsKey(key)) {
			return ValueType.NOT_SUPPORT;
		}
		return this.datas.get(key).getType();
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> retMap = new HashMap<>();
		Set<String> keySet = this.datas.keySet();
		for (String key : keySet) {
			ValueType type = datas.get(key).getType();
			if (type == DataMap.ValueType.BIG_DECIMAL) {
				retMap.put(key,getBigDecimal(key));
			} else if (type == DataMap.ValueType.FLOAT) {
				retMap.put(key, getFloat(key));
			} else if (type == DataMap.ValueType.STRING) {
				retMap.put(key, getString(key));
			} else if (type == DataMap.ValueType.BOOL) {
				retMap.put(key, getBoolean(key));
			} else if (type == DataMap.ValueType.INT) {
				retMap.put(key, getInteger(key));
			} else if (type == DataMap.ValueType.LONG) {
				retMap.put(key, getLong(key));
			} else if (type == DataMap.ValueType.BIG_INTEGER) {
				retMap.put(key, getBigInteger(key));
			} else if (type == DataMap.ValueType.DATA_MAP) {
				retMap.put(key, getDataMap(key).toMap());
			} else if (type == DataMap.ValueType.DATA_LIST) {
				retMap.put(key, getDataList(key).toListMap());
			}
		}
		return retMap;
	}

	class MapValue {
		ValueType type;
		Object value;
		
		MapValue(Object value, ValueType type) {
			this.value = value;
			this.type = type;
		}

		public ValueType getType() {
			return type;
		}

		public void setType(ValueType type) {
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
		
	}
}
