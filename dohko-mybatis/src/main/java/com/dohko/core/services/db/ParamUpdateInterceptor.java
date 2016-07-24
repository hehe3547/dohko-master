package com.dohko.core.services.db;

import com.dohko.core.base.DataMap;
import com.dohko.core.util.DateUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.util.Map;
import java.util.Properties;


@Intercepts({@Signature(type=Executor.class,method="update",args={MappedStatement.class, Object.class})})
public class ParamUpdateInterceptor extends ParamInterceptor {

	public void setProperties(Properties properties) {
		
	}

	protected Map<String, Object> transBeforeParams(DataMap dataMap) {
		Map<String, Object> params = dataMap.toMap();
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

}
