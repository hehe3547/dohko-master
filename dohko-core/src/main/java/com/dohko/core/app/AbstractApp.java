package com.dohko.core.app;

import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.PropertyResolver;

/**
 * Created by xiangbin on 2016/6/22.
 */
public abstract class AbstractApp implements ApplicationContextAware {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationContext appContext;

    protected final String getPropertyValue(String key) {
        PropertyResolver resolver = this.appContext.getBean(PropertyResolver.class);
        return resolver.getProperty(key);
    }

    protected final void debug(String message) {
        LogUtils.debug(message, logger);
    }

    protected final void info(String message) {
        LogUtils.info(message, logger);
    }

    protected final void warn(String message){
        LogUtils.warn(message, logger);
    }

    protected final void error(String message) {
        LogUtils.error(message, logger);
    }

    protected final void error(Throwable e) {
        e.printStackTrace();
        LogUtils.errorException(e, logger);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

}
