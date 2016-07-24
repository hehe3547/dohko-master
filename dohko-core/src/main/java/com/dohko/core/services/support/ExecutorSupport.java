package com.dohko.core.services.support;



import com.dohko.core.PlatResultInfo;
import com.dohko.core.base.*;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.services.ExecInfo;
import com.dohko.core.services.Executor;
import com.dohko.core.services.annotation.AppService;
import com.dohko.core.services.annotation.Validate;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Map;

/**
 * 服务的执行，相当于之前的Component
 * @author xiangbin
 *
 */
@Component(Executor.BEAN_ID)
public class ExecutorSupport implements Executor, ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(ExecutorSupport.class);

	private String METHOD_NAME = "execute";
	private final String TX_MANAGER_NAME = "platform_txManager";
	@Autowired
	private ServerConfig serverConfig;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	private ApplicationContext appContext;

	
	@Override
	public Response execute(Request request) {
		String tranceId = request.getTranceId();
		LogUtils.info(request, "tranceId [" + tranceId + "]", logger);
		String name = request.getService();
		ExecInfo execInfo = this.appContext.getBean(ExecInfo.class);
		Map<String, String> coreServices = execInfo.getCoreServices();
		if (coreServices.containsKey(name)) {
			name = coreServices.get(name);
		}
		if (!appContext.containsBean(name) && !name.startsWith(serverConfig.getGroup())) {
			Response response = Response.Builder.create(tranceId, PlatResultInfo.SERVICE_NOT_FOUND);
			LogUtils.error(response, "service [" + name + "] error", logger);
			return response;
		}
		if (execInfo != null) {
			execInfo.serviceInfo(name, null);
		}
		if (request.getHolder() != null) {
			ContextHolder.setHolder(request.getHolder());
		} else {
			LogUtils.info(request, "not request holder .dohko.. ",logger);
		}
		BaseService baseService = appContext.getBean(name, BaseService.class);
		try {
			LogUtils.info("start execute service [" + name + "]", logger);
			LogUtils.info(request, "request data", logger);
			Response response = execute(baseService, name, tranceId, request.getDataMap(), request.getHolder());
			if (!response.success()) {
				LogUtils.error(request, response, "service [" + name + "]", logger);
			} else {
				LogUtils.info(request, response, "service [" + name + "]", logger);
			}
			return response;
		} catch (Throwable e) {
			LogUtils.error(request,  "service [" + name + "] error ",logger);
			LogUtils.errorException(e, logger);
			return Response.Builder.create(tranceId, PlatResultInfo.SYSTEM_ERROR);
		}
	}
	
	private Response execute(BaseService baseService, final String serviceName, final String tranceId, final DataMap dataMap, final Holder holder) {
		Class<?> serviceClass = baseService.getClass();
		AppService appService = serviceClass.getAnnotation(AppService.class);
		if (appService == null) {
			logger.error("not found service [" + serviceName +"] className [" + baseService.getClass() + "] AppServer ");
			return Response.Builder.create(tranceId, PlatResultInfo.CONFIG_APPSERVICE);
		}
		Validate validate = serviceClass.getAnnotation(Validate.class);
		if (validate != null) {
			LogUtils.debug(dataMap,  "validate [" + serviceName + "]", logger);
			ResultInfo validateInfo = validate(validate, dataMap);
			if (!validateInfo.success()) {
				LogUtils.error(validateInfo, "valiedate service [" + serviceName + "]", logger);
				return Response.Builder.create(tranceId, validateInfo);
			}
		} else {
			LogUtils.debug("service [" + serviceName +"] validate is null", logger);
		}
		LogUtils.info("start execute service [" + serviceName + "]", logger);
		ResultInfo resultInfo = PlatResultInfo.SUPPORT_METHOD;
		if (appService.transaction()) {
			LogUtils.info("service [" + serviceName + "] need transaction", logger);
			DefaultTransactionDefinition transDef = new DefaultTransactionDefinition();
			PlatformTransactionManager transactionManager = appContext.getBean(TX_MANAGER_NAME, PlatformTransactionManager.class);
			TransactionStatus status = transactionManager.getTransaction(transDef);
			resultInfo = simpleExecute(baseService, appService, dataMap, holder);
			if (!resultInfo.success()) {
				transactionManager.rollback(status);
			} else {
				transactionManager.commit(status);
			}
		} else {
			resultInfo = simpleExecute(baseService, appService, dataMap, holder);
		}
		if (!resultInfo.success()) {
			LogUtils.error(resultInfo, "execute service [" + serviceName + "]", logger);
			return Response.Builder.create(tranceId, resultInfo);
		} 
		LogUtils.debug(resultInfo, "execute service [" + serviceName + "]", logger);
		return Response.Builder.create(tranceId, resultInfo);
	}

	private ResultInfo simpleExecute(BaseService baseService, AppService appService, DataMap dataMap, Holder holder) {
		try {
			ResultInfo resultInfo = PlatResultInfo.SUPPORT_METHOD;
			if (appService.param().equals(AppService.Param.DATAMAP)) {
				resultInfo = baseService.execute(dataMap);
			} else if (appService.param().equals(AppService.Param.HOLDER)){
				resultInfo = baseService.execute(dataMap, holder);
			}
			return resultInfo;
		} catch (Throwable e) {
			LogUtils.error(dataMap, "service [" + appService.value()+ "] execute error", logger);
			LogUtils.error(holder, "", logger);
			LogUtils.errorException(e, logger);
			return PlatResultInfo.SYSTEM_ERROR;
		}
	}
	
	private ResultInfo validate(Validate validate, DataMap dataMap) {
		ServiceValidator validator = appContext.getBean(ServiceValidator.class);
		try {
			return validator.validate(validate, dataMap);
		} catch (Exception e) {
			LogUtils.errorException(e, logger);
		}
		return PlatResultInfo.SUCCESS;
	}

}
