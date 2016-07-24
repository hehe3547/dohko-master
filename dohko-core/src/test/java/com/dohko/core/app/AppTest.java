package com.dohko.core.app;

import com.dohko.core.app.bean.*;
import com.dohko.core.app.support.EventSupport;
import com.dohko.core.app.support.FilterSupport;
import com.dohko.core.base.DataMap;
import com.dohko.core.test.WebApplication;
import com.dohko.core.util.DataUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiangbin on 2016/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class AppTest implements ApplicationContextAware {

    private ApplicationContext appContext;

    @Test
    public void testNamespaceHandler() {
        EventBean eventBean = this.appContext.getBean("core_eventService", EventBean.class);
        Assert.assertEquals("check event type", "response", eventBean.getType().toString().toLowerCase());
        Assert.assertEquals("check event result", "success", eventBean.getResult().toString().toLowerCase());
        Assert.assertEquals("check event map bean", "kaas", eventBean.getRequest().getDatas().get(0).getKey());

        FilterBean filterBean = this.appContext.getBean("/base/simple.htm", FilterBean.class);
        Assert.assertEquals("check filter map bean", "kaaa", filterBean.getResponse().getDatas().get(0).getKey());
    }

    @Test
    public void doFilterResData() {
        FilterSupport support = new FilterSupport();
        FilterBean filterBean = this.appContext.getBean("/base/simple.htm", FilterBean.class);
        DataMap dataMap = support.doFilterMap(DataMap.Builder.create(), filterBean.getResponse(), new HashMap<String, Object>());
        System.out.println("filter dataMap [" + DataUtils.dataMap2json(dataMap) + "]");
    }

    @Test
    public void doEventResData() {
        EventSupport support = new EventSupport();
        EventBean eventBean = this.appContext.getBean("core_eventService", EventBean.class);
        DataMap dataMap = support.doFilterMap(DataMap.Builder.create(), eventBean.getRequest(), new HashMap<String, Object>());
        System.out.println("event dataMap [" + DataUtils.dataMap2json(dataMap) + "]");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
