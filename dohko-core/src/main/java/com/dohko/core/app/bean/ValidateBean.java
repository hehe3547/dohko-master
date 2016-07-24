package com.dohko.core.app.bean;

import java.util.List;

/**
 * Created by xiangbin on 2016/7/16.
 */
public class ValidateBean {

    public static String ELE_NAME = "validate";

    private final List<ParamBean> paramBeanList;
    public ValidateBean(List<ParamBean> paramBeanList) {
        this.paramBeanList = paramBeanList;
    }

    public List<ParamBean> getParamBeans() {
        return this.paramBeanList;
    }
}
