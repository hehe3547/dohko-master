package com.dohko.core.app.bean;

import com.dohko.core.base.DataMap;

/**
 * Created by xiangbin on 2016/6/26.
 */
public class EventBean {
    //请求事件，响应事件
    private Type type = Type.RESPONSE;

    private Result result = Result.SUCCESS;
    //执行条件
    private String condition;
    //请求数据
    private MapBean request;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public MapBean getRequest() {
        return request;
    }

    public void setRequest(MapBean request) {
        this.request = request;
    }

    public enum Type {
        REQUEST,
        RESPONSE
    }

    public enum Result {
        FAIL,
        SUCCESS,
        ALL
    }
}
