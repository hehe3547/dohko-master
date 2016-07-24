package com.dohko.core.web;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by xiangbin on 2016/7/13.
 */
public class BaseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Holder holder = getRequestHolder(request);
        request.setAttribute(Holder.Key.HOLDER.getKey(), holder);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        request.removeAttribute(Holder.Key.HOLDER.getKey());
    }

    protected Holder getRequestHolder(HttpServletRequest request) {
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
}
