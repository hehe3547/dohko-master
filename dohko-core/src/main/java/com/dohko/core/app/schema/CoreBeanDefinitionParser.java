package com.dohko.core.app.schema;

import com.dohko.core.app.Validator;
import com.dohko.core.app.bean.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


public class CoreBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> className;
    public CoreBeanDefinitionParser(Class<?> className) {
        this.className = className;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        if (className.equals(FilterBean.class)) {
            RootBeanDefinition filterDef = new RootBeanDefinition();
            filterDef.setBeanClass(this.className);
            filterDef.setLazyInit(false);
            String id = element.getAttribute("uri");
            if (element.hasAttribute("service")) {
                filterDef.getPropertyValues().addPropertyValue("service", element.getAttribute("service"));
            }
            parserContext.getRegistry().registerBeanDefinition(id, filterDef);
            if (element.hasAttribute("request")) {
                String reqData = element.getAttribute("request");
                filterDef.getPropertyValues().addPropertyValue("request", new RuntimeBeanReference(reqData));
            }
            if (element.hasAttribute("response")) {
                String resData = element.getAttribute("response");
                filterDef.getPropertyValues().addPropertyValue("response", new RuntimeBeanReference(resData));
            }
            if (element.hasAttribute("result")) {
                String result = element.getAttribute("result");
                filterDef.getPropertyValues().addPropertyValue("result", result);
            }
            NodeList nodeList = element.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String localName = node.getLocalName();
                if (("request".equals(localName) && !element.hasAttribute("request")) || ("response".equals(localName) && !element.hasAttribute("response"))) {
                    List<DataBean> dataBeanList = doDataParse((Element)node);
                    MapBean mapBean = new MapBean();
                    mapBean.setDatas(dataBeanList);
                    filterDef.getPropertyValues().addPropertyValue(localName, mapBean);
                }
                if (ValidateBean.ELE_NAME.equals(localName)) {
                    ValidateBean validateBean = doValidateParse((Element)node);
                    filterDef.getPropertyValues().addPropertyValue(localName, validateBean);
                }
            }
            return filterDef;
        } else if (className.equals(MapBean.class)) {
            RootBeanDefinition mapDef = new RootBeanDefinition();
            mapDef.setBeanClass(this.className);
            mapDef.setLazyInit(false);
            String id = element.getAttribute("id");
            parserContext.getRegistry().registerBeanDefinition(id, mapDef);
            List<DataBean> dataBeanList = doDataParse(element);
            mapDef.getPropertyValues().addPropertyValue("datas", dataBeanList);
            return mapDef;
        } else if (className.equals(EventBean.class)) {
            RootBeanDefinition eventDef = new RootBeanDefinition();
            eventDef.setBeanClass(this.className);
            eventDef.setLazyInit(false);
            String id = element.getAttribute("service");
            parserContext.getRegistry().registerBeanDefinition(id, eventDef);
            if (element.hasAttribute("type")) {
                eventDef.getPropertyValues().addPropertyValue("type", EventBean.Type.valueOf(element.getAttribute("type").toUpperCase()));
            }
            if (element.hasAttribute("result")) {
                eventDef.getPropertyValues().addPropertyValue("result", EventBean.Result.valueOf(element.getAttribute("result").toUpperCase()));
            }
            if (element.hasAttribute("condition")) {
                eventDef.getPropertyValues().addPropertyValue("condition", element.getAttribute("condition"));
            }
            if (element.hasAttribute("request")) {
                String reqData = element.getAttribute("request");
                eventDef.getPropertyValues().addPropertyValue("request", new RuntimeBeanReference(reqData));
            }
            NodeList nodeList = element.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String localName = node.getLocalName();
                if (("request".equals(localName) && !element.hasAttribute("request"))) {
                    List<DataBean> dataBeanList = doDataParse((Element)node);
                    eventDef.getPropertyValues().addPropertyValue(localName, new MapBean(dataBeanList));
                }
            }
        }
        return null;
    }

	protected Class getBeanClass(Element element) {  
        return FilterBean.class;  
    } 

    private ValidateBean doValidateParse(Element validateEle) {
        List<ParamBean> retList = new ArrayList<>();
        NodeList dataNodeList = validateEle.getChildNodes();
        for (int i = 0; i < dataNodeList.getLength(); i++) {
            Node node = dataNodeList.item(i);
            if (ParamBean.ELE_NAME.equals(node.getLocalName())) {
                Element data = ((Element)node);
                if (data.hasAttribute(ParamBean.ATTR_KEY) && data.hasAttribute(ParamBean.ATTR_RULE)) {
                    String key = data.getAttribute(ParamBean.ATTR_KEY);
                    String rule = data.getAttribute(ParamBean.ATTR_RULE);
                    ParamBean paramBean = new ParamBean(key, Validator.Rule.valueOf(rule.toUpperCase()));
                    if (data.hasAttribute(ParamBean.ATTR_VARIABLE)) {
                        paramBean.setParams(data.getAttribute(ParamBean.ATTR_VARIABLE));
                    }
                    retList.add(paramBean);
                }
            }
        }
        return new ValidateBean(retList);
    }

    private List<DataBean> doDataParse(Element listEle) {
        List<DataBean> retList = new ArrayList<>();
        NodeList dataNodeList = listEle.getChildNodes();
        for (int i = 0; i < dataNodeList.getLength(); i++) {
            Node node = dataNodeList.item(i);
            if (DataBean.ELE_NAME.equals(node.getLocalName())) {
                Element data = ((Element)node);
                String key = data.getAttribute(DataBean.ATTR_KEY);
                DataBean dataBean = new DataBean(key);
                if (data.hasAttribute("value")) {
                    dataBean.setValue(data.getAttribute("value"));
                } else if (data.hasAttribute("source")) {
                    dataBean.setSource(data.getAttribute("source"));
                } else if (data.hasAttribute("expression")) {
                    dataBean.setExpression(data.getAttribute("expression"));
                }
                if (data.hasAttribute("type")) {
                    dataBean.setType(DataBean.Type.valueOf(data.getAttribute("type").toUpperCase()));
                }
                if (data.hasAttribute("pattern")) {
                    dataBean.setPattern(data.getAttribute("pattern"));
                }
                NodeList childList = data.getChildNodes();
                for (int j = 0; j < childList.getLength(); j++) {
                    Node childNode = childList.item(j);
                    String name = childNode.getLocalName();
                    if ("map".equals(name)) {
                        Element childEle = (Element)childNode;
                        List<DataBean> childDataBeanList = doDataParse(childEle);
                        MapBean mapBean = new MapBean(childDataBeanList);
                        if (childEle.hasAttribute("original")) {
                            mapBean.setOriginal(Boolean.parseBoolean(childEle.getAttribute("original")));
                        }
                        dataBean.setMapBean(mapBean);
                    } else if ("list".equals(name)) {
                        Element childEle = (Element)childNode;
                        List<DataBean> childDataBeanList = doDataParse(childEle);
                        ListBean listBean = new ListBean(childDataBeanList);
                        if (childEle.hasAttribute("original")) {
                            listBean.setOriginal(Boolean.parseBoolean(childEle.getAttribute("original")));
                        }
                        if (childEle.hasAttribute("count")) {
                            listBean.setCount(Integer.parseInt(childEle.getAttribute("count")));
                        }
                        if (childEle.hasAttribute("index")) {
                            listBean.setIndex(childEle.getAttribute("index"));
                        }
                        dataBean.setListBean(listBean);
                    }
                }
                retList.add(dataBean);
            }
        }
        return retList;
    }


}
