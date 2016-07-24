package com.dohko.core.services.db;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.app.Validator;
import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.annotation.AppService;
import com.dohko.core.services.annotation.Validate;
import com.dohko.core.services.annotation.Validate.*;
import com.dohko.core.services.support.BaseServiceSupport;
import org.apache.ibatis.binding.MapperProxy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * 简单处理mybatis的增删改查，直接调用Mapper
 * Created by xiangbin on 2016/6/25.
 */
@AppService(value = "core_execSimpleMapper")
@Validate({@Param(value="mapper", rules = {Validator.Rule.REQUIRED}),
          @Param(value="method", rules={Validator.Rule.REQUIRED})})
public class SimpleExecuteMapper extends BaseServiceSupport implements ApplicationContextAware {

    private ApplicationContext appContext;

    public ResultInfo execute(DataMap dataMap) {
        String mapperClassName = dataMap.getString("mapper");
        String methodName = dataMap.getString("method");
        String paramName = dataMap.getString("param", DataMap.class.getName());
        String paramKey = dataMap.getString("paramKey", "");
        info("execute mapper className [" + mapperClassName + "] methodName [" + methodName + "] paramName [" + paramName + "]");
        try {
            Class<?> mapperClass = Class.forName(mapperClassName);
            //取mybatis的代理类
            Proxy mapperObj = (Proxy)appContext.getBean(mapperClass);
            MapperProxy mapperProxy = (MapperProxy)Proxy.getInvocationHandler(mapperObj);
            Class<?> paramClass = Class.forName(paramName);
            Method method = mapperClass.getMethod(methodName, new Class[] {paramClass});
            if (method != null && mapperObj != null) {
                Object[] paramObj = new Object[] {dataMap};
                if (!paramName.equals(DataMap.class.getName()) && !"".equals(paramKey)) {
                    paramObj = new Object[] {dataMap.getString(paramKey)};
                }
                Object result = mapperProxy.invoke(mapperObj, method, paramObj);
                if (result == null) {
                    return dataMap;
                } else if (result instanceof List) {
                    return DataList.Builder.create().addAll((List)result);
                } else if (result instanceof DataMap) {
                    return (DataMap)result;
                } else if (result instanceof DataList) {
                    return (DataList)result;
                } else if (result instanceof Map){
                    return DataMap.Builder.create((Map<String, Object>) result);
                } else {
                    error("not support result type [" + result.getClass() + "]");
                }
            } else {
                return PlatResultInfo.MAPPER_NOT_FOUND.formatResultMessage(new Object[] {mapperClassName, methodName, paramName});
            }
        } catch (Throwable e) {
            error(e);
            return PlatResultInfo.SYSTEM_ERROR;
        }
        return dataMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
