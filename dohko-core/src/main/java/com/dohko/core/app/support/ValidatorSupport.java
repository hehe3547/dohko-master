package com.dohko.core.app.support;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.app.Validator;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangbin on 2016/7/16.
 */
public class ValidatorSupport implements Validator {

    private Logger logger = LoggerFactory.getLogger(ValidatorSupport.class);
    private static Map<Rule, DataValidator> validateCache = new HashMap<>();

    static {
        validateCache.put(Rule.INT, new IntValidator());
        validateCache.put(Rule.REQUIRED, new RequiredValidator());
        validateCache.put(Rule.MAX, new MaxValidator());
        validateCache.put(Rule.MIN, new MinValidator());
    }

    @Override
    public ResultInfo validate(Rule rule, String key, DataMap dataMap, String params) {
        if (!validateCache.containsKey(rule)) {
            logger.error("not support rule [" + rule + "]");
            return PlatResultInfo.SUCCESS;
        }
        DataValidator validator = validateCache.get(rule);
        if (logger.isDebugEnabled()) {
            logger.debug("validate rule [" + rule + "] key [" + key + "] validator [" + validator.getClass() + "] params [" + params + "]");
        }
        ResultInfo resultInfo = validator.validate(key, dataMap, params);
        if (!resultInfo.success()) {
            logger.error("validate key [" + key + "] returnMessage [" + resultInfo.resultMessage() + "]");
        }
        return resultInfo;
    }

    private interface DataValidator {
        ResultInfo SUCCESS = PlatResultInfo.SUCCESS;
        ResultInfo validate(String key, DataMap dataMap, String params);
    }

    private static class RequiredValidator implements DataValidator {

        @Override
        public ResultInfo validate(String key, DataMap dataMap, String params) {
            if (!dataMap.contains(key)) {
                return PlatResultInfo.VALIDATE_REQUIRED.formatResultMessage(new String[] {key});
            }
            return SUCCESS;
        }

    }

    private static class IntValidator  implements DataValidator {
        @Override
        public ResultInfo validate(String key, DataMap dataMap, String params) {
            if (dataMap.contains(key) && dataMap.getInteger(key) == null) {
                return PlatResultInfo.VALIDATE_INT.formatResultMessage(new String[] {key});
            }
            return SUCCESS;
        }
    }

    private static class RequiredIntValidator extends IntValidator {
        @Override
        public ResultInfo validate(String key, DataMap dataMap, String params) {
            if (!dataMap.contains(key)) {
                return PlatResultInfo.VALIDATE_REQUIRED.formatResultMessage(new String[] {key});
            }
            return super.validate(key, dataMap, params);
        }
    }

    private static class MinValidator  implements DataValidator {

        @Override
        public ResultInfo validate(String key, DataMap dataMap, String params) {
            if (dataMap.contains(key) && DataUtils.isDigit(params)) {
                int min = Integer.parseInt(params);
                String value = dataMap.getString(key);
                if (min != -1 && value.length() < min) {
                    return PlatResultInfo.VALIDATE_MIN.formatResultMessage(new Object[] {key, params});
                }
            }
            return SUCCESS;
        }
    }

    private static class MaxValidator implements DataValidator {

        @Override
        public ResultInfo validate(String key, DataMap dataMap, String params) {
            if (dataMap.contains(key) && DataUtils.isDigit(params)) {
                String value = dataMap.getString(key);
                int max = Integer.parseInt(params);
                if (max != -1 && value.length() > max) {
                    return PlatResultInfo.VALIDATE_MAX.formatResultMessage(new Object[] {key, params});
                }
            }
            return SUCCESS;
        }
    }
}
