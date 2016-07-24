package com.dohko.core.app.bean;

import com.dohko.core.app.Validator.Rule;

/**
 * Created by xiangbin on 2016/7/16.
 */
public class ParamBean {

    public static String ELE_NAME = "param";
    public static String ATTR_KEY = "key";
    public static String ATTR_RULE = "rule";
    public static String ATTR_VARIABLE = "variable";

    private final String key;

    private final Rule rule;

    private String params;

    public ParamBean(String key, Rule rule) {
        this.key = key;
        this.rule = rule;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getKey() {
        return key;
    }

    public Rule getRule() {
        return rule;
    }
}
