package com.dohko.core.services.support;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.app.support.ValidatorSupport;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.annotation.Validate;
import com.dohko.core.services.annotation.Validate.Param;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author xiangbin
 *
 */
@Component
public class ServiceValidator extends ValidatorSupport {
	
	private Logger logger = LoggerFactory.getLogger(ServiceValidator.class);

	public ResultInfo validate(Validate validate, DataMap dataMap) {
		StringBuilder errorMsg = new StringBuilder();
		Param[] params = validate.value();
		for (Param param : params) {
			LogUtils.debug( "validate param [" + param + "]", logger);
			ResultInfo validateInfo = validate(param, dataMap);
			if (!validateInfo.success()) {
				errorMsg.append(validateInfo.resultMessage()).append(";");
			}
		}
		if (errorMsg.length() > 0) {
			if (logger.isErrorEnabled()) {
				logger.error("param dataMap [" + dataMap + "] validate error");
			}
			return PlatResultInfo.VALIDATE_SERVICE_ERROR.formatResultMessage(new Object[] {errorMsg.toString()});
		}
		return PlatResultInfo.SUCCESS;
	}
	
	private ResultInfo validate(Param param, DataMap  dataMap) {
		Rule[] rules = param.rules();
		int variableIndex = -1;
		for (Rule rule : rules) {
			if (logger.isDebugEnabled()) {
				logger.debug("validate key [" + param.value() + "] rule [" + rule + "] params [" + rule.variable() + "]");
			}
			ResultInfo resultInfo = validate(rule, param.value(), dataMap, rule.variable() ? param.variable()[++variableIndex] : "");
			if (!resultInfo.success()) {
				if (logger.isErrorEnabled()) {
					logger.error("validate [" + param.value() + "] failure, result [" + resultInfo + "] ");
				}
				return resultInfo;
			}
		}
		return PlatResultInfo.SUCCESS;
	}

}


