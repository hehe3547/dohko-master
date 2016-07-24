package com.dohko.core.services.db;

import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(type=Executor.class,method="query",args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ResultQueryInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
		Object object = invocation.proceed();
		List<ResultMap> resultMaps = mappedStatement.getResultMaps();
		if (resultMaps.size() == 1) {
			ResultMap resultMap = resultMaps.get(0);
			if (resultMap.getType() == DataList.class || resultMap.getType() == DataMap.class) {
				List<DataMap> dataMapList = transResult(object);
				if (resultMap.getType() == DataList.class) {
					List<DataList> retList = new ArrayList<>();
					retList.add(DataList.Builder.create(dataMapList));
					return retList;
				}
				return dataMapList;
			}
		}
		return object;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		
	}

	@SuppressWarnings("unchecked")
	protected List<DataMap> transResult(Object object) {
		List<DataMap> retList = new ArrayList<>();
		if (object instanceof Map) {
			DataMap dataMap = DataMap.Builder.create().putAll((Map)object);
			retList.add(dataMap);
		} else if ((object instanceof List)) {
			List<Map<String, Object>> list = (List<Map<String,Object>>)object;
			for (Map<String, Object> resultMap : list) {
				retList.add(DataMap.Builder.create().putAll(resultMap));
			}
		}
 		return retList;
	}
	
	@SuppressWarnings("unchecked")
	protected List<DataMap> transResultMap(Object object) {
		List<DataMap> retList = new ArrayList<>();
		if (object instanceof Map){
			DataMap dataMap = DataMap.Builder.create().putAll((Map)object);
			retList.add(dataMap);
		} else if (object instanceof List) {

		}
		return retList;
	}

}
