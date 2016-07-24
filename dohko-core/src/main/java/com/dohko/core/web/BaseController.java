package com.dohko.core.web;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.app.AbstractApp;
import com.dohko.core.app.Filter;
import com.dohko.core.app.Security;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.base.Response;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.Client;
import com.dohko.core.util.DateUtils;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by xiangbin on 2016/7/5.
 */
public class BaseController extends AbstractApp implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected ApplicationContext appContext;

    protected  ResultInfo time() {
        DataMap retMap = DataMap.Builder.create().put("time", DateUtils.getCurrentDate());
        return retMap;
    }

    public ResultInfo execute(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        DataMap reqData = getRquestData(httpReq);
        Holder holder = getRequestHolder();
        String uri = holder.getValue(Holder.Key.URI);
        LogUtils.info(reqData, "request data", logger);
        ResultInfo checkResult = validate(uri, reqData, holder);
        if (!checkResult.success()) {
            LogUtils.error(checkResult, "check serviceReq error", logger);
            return checkResult;
        }
        String serviceName = doFilterName(uri, reqData, holder);
        LogUtils.info("serviceName [" + serviceName + "]", logger);
        if (Filter.RESULT_SERVICE_NAME.equals(serviceName)) {
            DataMap resDs = doResultFilter(uri, reqData, reqData, holder);
            return resDs;
        }
        DataMap reqDs = doRequestFilter(uri, reqData, holder);
        DataMap resultInfo = doReqRes(serviceName, reqDs, holder);
        if (!resultInfo.success()) {
            LogUtils.error(reqDs, "request data", logger);
            LogUtils.error(resultInfo, "response data", logger);
            return resultInfo;
        }
        DataMap resDs = doResultFilter(uri, reqData, (DataMap)resultInfo, holder);
        return resDs;
    }

    protected ResultInfo validate(String id, DataMap dataMap, Holder holder) {
        Security security = this.appContext.getBean(Security.class);
        if (security != null) {
            return security.validate(id, dataMap, holder);
        }
        return PlatResultInfo.SUCCESS;
    }

    private String doFilterName(String id, DataMap dataset, Holder holder) {
        Filter filter = this.appContext.getBean(Filter.class);
        if (filter != null) {
            String serviceName = filter.doFilterName(id, dataset, holder);
            return serviceName;
        }
        return id;
    }

    private DataMap doRequestFilter(String id, DataMap dataset, Holder holder) {
        Filter filter = this.appContext.getBean(Filter.class);
        if (filter != null) {
            return filter.doReqFilter(id, dataset, holder);
        }
        return dataset;
    }

    private DataMap doResultFilter(String id, DataMap reqDs, DataMap resDs, Holder holder) {
        Filter filter = this.appContext.getBean(Filter.class);
        if (filter != null) {
            return filter.doResFilter(id, reqDs, resDs, holder);
        }
        return resDs;
    }

    protected ResultInfo execute(String serviceName, DataMap dataMap, Holder holder) {
        DataMap resMap = doReqRes(serviceName, dataMap, holder);
        return resMap;
    }

    protected DataMap doReqRes(String serviceName, DataMap dataMap, Holder holder) {
        return Client.Builder.execute(serviceName, dataMap, holder);
    }

    private DataMap getRquestData(HttpServletRequest request) {
        DataMap dataMap = DataMap.Builder.create().put("key", "value");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String value = request.getParameter(key);
            dataMap.put(key, value);
        }
        return dataMap;
    }

    protected Holder getRequestHolder() {
        HttpServletRequest request = getHttpRequest();
        DataMap dataMap = DataMap.Builder.create();
        dataMap.put(Holder.Key.IP.getKey(), getRemoteIp(request));
        dataMap.put(Holder.Key.PORT.getKey(), request.getRemotePort());
        dataMap.put(Holder.Key.URI.getKey(), request.getRequestURI());
        dataMap.put(Holder.Key.TRANCEID.getKey(), UUID.randomUUID().toString());
        return Holder.Builder.create(dataMap);
    }

    private String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.startsWith("unknown")) {
            ip = ip.substring(ip.indexOf("unknown") + "unknown".length());
        }
        ip = ip.trim();
        if (ip.startsWith(",")) {
            ip = ip.substring(1);
        }
        if (ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    protected Response toResponse(ResultInfo resultInfo, Holder holder) {
        if (holder != null) {
            return Response.Builder.create(holder.getValue(Holder.Key.TRANCEID), resultInfo);
        }
        return Response.Builder.create(UUID.randomUUID().toString(), resultInfo);
    }

    protected HttpServletRequest getHttpRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getHttpResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
