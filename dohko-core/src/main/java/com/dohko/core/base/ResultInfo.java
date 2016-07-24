package com.dohko.core.base;

public interface ResultInfo {
	
	boolean success();
	
	String resultCode();
	
	String resultMessage();
	
	ResultInfo formatResultMessage(Object[] formatParams);
	
	final class Builder {
		
		public static ResultInfo create(String resultCode, String resultMsg) {
			return new ResultInfoImpl(resultCode, resultMsg);
		}
		
		public static DataMap createDataMap(String resultCode, String resultMsg) {
			return new DataMapImpl(resultCode, resultMsg);
		}
	}
}
