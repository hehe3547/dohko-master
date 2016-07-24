package com.dohko.core.services.service;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.annotation.AppService;
import com.dohko.core.services.support.BaseServiceSupport;
import com.dohko.core.util.NetUtils;

/**
 * Created by xiangbin on 2016/7/5.
 */
@AppService("core_simpleService")
public class SimpleService extends BaseServiceSupport implements IgnoreService {

    @Override
    public ResultInfo execute(DataMap dataMap) {
        dataMap.put("threadName", Thread.currentThread().getName());
        dataMap.put("hostname", NetUtils.getHostAddress());
        return dataMap;
    }
}
