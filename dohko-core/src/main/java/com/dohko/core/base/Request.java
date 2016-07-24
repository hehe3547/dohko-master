package com.dohko.core.base;

import java.util.Map;

/**
 * 请求数据集
 * @author xiangbin
 *
 */
public interface Request {
	/**
	 * 执行的SERVICE ID
	 * @return
	 */
	String getService();

	String getTranceId();

	DataMap getDataMap();

	Request setDataMap(DataMap dataMap);

	Holder getHolder();
	
	Request setHolder(Holder userHolder);

	Map<String, Object> toMap();
	
	final class Builder {
		
		public static Request create(String serviceName, DataMap dataMap) {
			return new RequestImpl(serviceName, dataMap, null);
		}
		
		public static Request create(String serviceName, DataMap dataMap, Holder holder) {
			return new RequestImpl(serviceName, dataMap, holder);
		}
	}
}
