package com.dohko.core.base;

import java.util.Map;

/**
 * 响应数据接口
 * @author xiangbin
 *
 */
public interface Response {	

	boolean success();

	String getTranceId();
	
	String getReturnCode();

	String getReturnMessage();

	DataMap getDataMap();

	Response setDataMap(DataMap dataMap);

	Map<String, Object> toMap();
	
	final class Builder {
		
		public static Response create(String tranceId, ResultInfo resultInfo) {
			return new ResponseImpl(tranceId, resultInfo);
		}

		public static Response create(String tranceId, String returnCode, String retMsg) {
			return new ResponseImpl(tranceId, returnCode, retMsg);
		}

	}
}
