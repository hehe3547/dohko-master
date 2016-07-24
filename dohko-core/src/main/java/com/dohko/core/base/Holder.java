package com.dohko.core.base;

import java.util.Map;


/**
 * 用户SESSION信息
 * @author xiangbin
 *
 */
public interface Holder {

	enum Key {
		HOLDER("_HOLDER_"),
		IP("_IP_"),
		PORT("_PORT_"),
		CHANNEL("_CHANNEL_"),
		URI("_URI_"),
		TRANCEID("_TRANCEID_");
		private String key;
		private Key(String key) {
			this.key = key;
		}
		public String getKey() {
			return this.key;
		}
	}
	String getValue(String key);
	String getValue(Key key);

	DataMap getDataMap();

	Map<String, Object> toMap();

	public static class Builder {
		public static Holder create(DataMap dataMap) {
			return new HolderImpl(dataMap);
		}
	}
}
