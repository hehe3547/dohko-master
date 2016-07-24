package com.dohko.core.app.bean;

/**
 * Created by xiangbin on 2016/6/12.
 */
public class DataBean {
    public static final String ELE_NAME = "data";
    public static final String ATTR_KEY = "key";
    public static final String ATTR_VALUE = "key";
    public static final String ATTR_SOURCE = "key";
    public static final String ATTR_EXPRESSION = "key";
    public static final String ATTR_TYPE = "key";
    public static final String ATTR_PATTERN = "key";
    public static final String ATTR_SOURCE_PATTERN = "key";
    public static final String ATTR_MAP = "key";
    public static final String ATTR_LIST = "key";
    private final  String key;
    private String value;
    private String source;
    private String expression;
    private Type type;
    private String pattern;
    private String sourcePattern = "yyyyMMddHHmmss";
    private MapBean mapBean;
    private ListBean listBean;

    public enum Type {
        INT,
        DECIMAL,
        DATE,
        JSON
    }

    public MapBean getMapBean() {
        return mapBean;
    }

    public void setMapBean(MapBean mapBean) {
        this.mapBean = mapBean;
    }

    public ListBean getListBean() {
        return listBean;
    }

    public void setListBean(ListBean listBean) {
        this.listBean = listBean;
    }

    public DataBean(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getKey() {
        return key;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getSourcePattern() {
        return sourcePattern;
    }

    public void setSourcePattern(String sourcePattern) {
        this.sourcePattern = sourcePattern;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("key [" + key + "] value [");
        if (this.value != null) {
            sb.append("value:"+this.value);
        } else if (this.source != null) {
            sb.append("source:" + this.source);
        } else if (this.expression != null) {
            sb.append("expression:" + this.expression);
        } else if (this.mapBean != null) {
            sb.append("map:" + this.mapBean.toString());
        } else if (this.listBean != null) {
            sb.append("list:" + this.listBean.toString());
        }
        sb.append("]");
        if (this.type != null) {
            sb.append(" type [" + this.type.toString() + "]");
            if (this.pattern != null) {
                sb.append(" pattern [" + this.pattern + "]");
            }
            if (type == Type.DATE) {
                if (this.sourcePattern != null) {
                    sb.append(" sourcePattern [" + this.sourcePattern + "]");
                }
            }
        }
        return sb.toString();
    }
}
