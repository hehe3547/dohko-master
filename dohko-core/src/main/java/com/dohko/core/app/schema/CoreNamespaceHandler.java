package com.dohko.core.app.schema;

import com.dohko.core.app.bean.*;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CoreNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("filter", new CoreBeanDefinitionParser(FilterBean.class));
		registerBeanDefinitionParser("map", new CoreBeanDefinitionParser(MapBean.class));
		registerBeanDefinitionParser("event", new CoreBeanDefinitionParser(EventBean.class));
	}

}
