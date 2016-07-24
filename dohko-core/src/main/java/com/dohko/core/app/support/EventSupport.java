package com.dohko.core.app.support;

import com.dohko.core.app.Event;
import com.dohko.core.app.bean.EventBean;
import com.dohko.core.app.bean.MapBean;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.util.LogUtils;
import com.dohko.core.util.OgnlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/29.
 */
public class EventSupport extends MapFilterSupport implements Event, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(EventSupport.class);

    private ApplicationContext appContext;

    @Override
    public void doReqEvent(String service, DataMap dataMap, Holder holder) {

    }

    @Override
    public void doResEvent(String service, DataMap reqData, DataMap resData, Holder holder) {
        EventBean eventBean = appContext.getBean(service, EventBean.class);
        if (EventBean.Type.RESPONSE != eventBean.getType()) {
            return;
        }
        if (EventBean.Result.ALL == eventBean.getResult() || (EventBean.Result.SUCCESS == eventBean.getResult() && resData.success())
                  || (EventBean.Result.FAIL == eventBean.getResult() && !resData.success())) {
            return;
        }
        Map<String, Object> ognl = new HashMap<>();
        if (eventBean.getCondition() != null) {
            if (!(Boolean) OgnlUtils.toValue(eventBean.getCondition(), ognl, resData)) {
                return ;
            }
        }
        MapBean mapBean = eventBean.getRequest();
        DataMap dataMap = doFilterMap(resData, mapBean, ognl);
        LogUtils.info(dataMap, "response event data", logger);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
