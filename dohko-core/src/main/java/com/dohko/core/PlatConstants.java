package com.dohko.core;

/**
 * 平台基础提供的全局变量
 * @author xiangbin
 *
 */
public interface PlatConstants {
	
	String JSON_RECORDS = "records";
	//JSON响应
	public final static String JSON_RESPONSE_DATA = "data";
	
	public final static String JSON_PAGE_COUNT = "pageCount";
	
	public final static String JSON_PAGE_NO = "pageNo";
	
	public final static String JSON_TOTAL_SIZE = "totalSize";
	
	public final static String JSON_PAGE_SIZE = "pageSize";
	
	public final static String JSON_RESPONSE_RECORDS = "records";
	
	//返回消息成功
	public final static String RESPONSE_SUCCESS_RETCODE = "000";
	
	public final static String RESPONSE_SUCCESS_RETMSG = "SUCCESS";
	
	public final static String RESPONSE_FAIL_RETCODE = "SP00002";
	
	public final static String RESPONSE_FAIL_RETMSG = "FAIL";
	//返回非指定异常
	public final static String RESPONSE_ERROR = "SP00001";
	//返回指定异常
	public final static String RESPONSE_NOSECURITY = "SP00004";
	//返回非指定异常
	public final static String RESPONSE_NOLONGING = "SP00003";
	
	public final static String TIMEOUT_CODE = "HSP00007";
	public final static String TIMEOUT_MSG = "请求服务超时";

	String KEY_GROUP = "group";
	String KEY_PORT = "port";
	String KEY_HOST = "host";
	String KEY_DATA = "data";
	String KEY_SERVICES = "service";

}
