package com.dohko.core.services.db;

import com.dohko.core.base.DataMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.Properties;

/**
 * Dataset设置参数
 * @author xiangbin
 *
 */
@Intercepts({@Signature(type=Executor.class,method="query",args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ParamQueryInterceptor extends ParamInterceptor {

	@Override
	public void setProperties(Properties properties) {

	}

	@Override
	protected Map<String, Object> transBeforeParams(DataMap dataMap) {
		Map<String, Object> params = dataMap.toMap();
		int pageNo = dataMap.getIntValue(DataMap.PAGE_NO, 0);
		int pageSize = dataMap.getIntValue(DataMap.PAGE_SIZE,0);
		if (pageNo >= 0 && pageSize > 0) {
			params.put("pageOffset", ((pageNo) - 1) * pageSize);
			params.put("pageSize", pageSize);
		}
		return params;
	}
}
