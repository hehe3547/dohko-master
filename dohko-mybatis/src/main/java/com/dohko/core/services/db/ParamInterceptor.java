package com.dohko.core.services.db;

import com.dohko.core.base.DataMap;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.DateUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public abstract class ParamInterceptor implements Interceptor {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getArgs()[1] instanceof DataMap) {
			DataMap sourceDs = (DataMap)invocation.getArgs()[1];
			Map<String, Object> params = transBeforeParams(sourceDs);
			invocation.getArgs()[1] = params;
			Object result = invocation.proceed();
			invocation.getArgs()[1] = transAfterParams(sourceDs, params);
			return result;
		} else {
			return invocation.proceed();
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	protected Map<String, Object> transBeforeParams(DataMap dataMap) {
		Map<String, Object> params = dataMap.toMap();
		int pageNo = dataMap.getIntValue(DataMap.PAGE_NO, 0);
		int pageSize = dataMap.getIntValue(DataMap.PAGE_SIZE,0);
		if (pageNo >= 0 && pageSize > 0) {
			params.put("pageOffset", ((pageNo) - 1) * pageSize);
			params.put("pageSize", pageSize);
		}
		if (!params.containsKey("action")) {
			params.put("action", "0");
		}
		String dateTime = DateUtils.getCurrentDateTime();
		if (!params.containsKey("actionTime")) {
			params.put("actionTime", dateTime);
		}
		if (!params.containsKey("createTime")) {
			params.put("createTime", dateTime);
		}
		params.put("currentTime", dateTime);
		return params;
	}

	protected DataMap transAfterParams(DataMap dataMap, Map<String, Object> params) {
		for (String key : params.keySet()) {
			if (!dataMap.contains(key)) {
				dataMap.put(key, params.get(key));
			}
		}
		return dataMap;
	}

}

