package com.dohko.core.app.support;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.app.Filter;
import com.dohko.core.app.Security;
import com.dohko.core.app.bean.FilterBean;
import com.dohko.core.app.bean.MapBean;
import com.dohko.core.app.bean.ParamBean;
import com.dohko.core.app.bean.ValidateBean;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.base.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/15.
 */
public class FilterSupport  extends MapFilterSupport implements Filter, Security, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(FilterSupport.class);
    private ApplicationContext appContext;

    @Override
    public String doFilterName(String id, DataMap dataMap, Holder holder) {
        if (appContext.containsBean(id)) {
            FilterBean filterBean = appContext.getBean(id, FilterBean.class);
            if (filterBean.isResult()) {
                return Filter.RESULT_SERVICE_NAME;
            }
            return filterBean.getService();
        }
        return null;
    }

    @Override
    public DataMap doReqFilter(String id, DataMap dataMap, Holder holder) {
        if (appContext.containsBean(id)) {
            FilterBean filterBean = appContext.getBean(id, FilterBean.class);
            MapBean reqBean = filterBean.getRequest();
            if (reqBean != null) {
                Map<String, Object> ognl = new HashMap<>();
                return doFilterMap(dataMap, reqBean, ognl);
            }
        }
        return dataMap;
    }

    @Override
    public DataMap doResFilter(String id, DataMap reqMap, DataMap resMap, Holder holder) {
        if (appContext.containsBean(id)) {
            FilterBean filterBean = appContext.getBean(id, FilterBean.class);
            MapBean resBean = filterBean.getResponse();
            if (resBean != null) {
                Map<String, Object> ognl = new HashMap<>();
                return doFilterMap(resMap, resBean, ognl);
            }
        }
        return resMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    @Override
    public ResultInfo validate(String id, DataMap dataMap, Holder holder) {
        if (appContext.containsBean(id) && (appContext.getBean(id) instanceof FilterBean)) {
            FilterBean filterBean = appContext.getBean(id, FilterBean.class);
            //验证参数
            ValidateBean validateBean = filterBean.getValidate();
            StringBuilder errorMsg = new StringBuilder();
            if (validateBean != null && validateBean.getParamBeans().size() > 0) {
                for (ParamBean paramBean : validateBean.getParamBeans()) {
                    ResultInfo resultInfo = validate(paramBean.getRule(), paramBean.getKey(), dataMap, paramBean.getParams());
                    if (!resultInfo.success()) {
                        errorMsg.append(resultInfo.resultMessage()).append(";");
                    }
                }
            }
            if (errorMsg.length() > 0) {
                return PlatResultInfo.VALIDATE_FILTE_ERROR.formatResultMessage(new Object[] {errorMsg.toString()});
            }
            if (filterBean.isResult() || (filterBean.getService() != null && !"".equals(filterBean.getService().trim()))) {
                return PlatResultInfo.SUCCESS;
            }
        }
        return PlatResultInfo.SERVICE_NO_SECURITY;
    }
}
