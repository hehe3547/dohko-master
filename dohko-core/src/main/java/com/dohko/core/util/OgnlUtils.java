package com.dohko.core.util;

import ognl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiangbin on 2016/6/26.
 */
public class OgnlUtils {

    private static Logger logger = LoggerFactory.getLogger(OgnlUtils.class);

    private static final Map<String, ognl.Node> expressionCache = new ConcurrentHashMap<>();

    /**
     * 解析Ognl表达式
     * @param expression
     * @param context
     * @param root
     * @return
     */
    public static Object toValue(String expression, Map<String, Object> context, Object root) {
        return toValue(expression, context, root, true);
    }

    public static Object toValue(String expression, Map<String, Object> context, Object root, boolean cache) {
        if (expression == null || expression.trim().equals("")) {
            return expression;
        }
        //解析表达式
        Object value = null;
        try {
            if (cache) {
                value = Ognl.getValue(parseExpression(expression), context, root);
            } else {
                value = Ognl.getValue(expression, context, root);
            }
            return value;
        } catch (Exception e) {
            logger.error("expression [" + expression + "] context [" + context + "]");
            logger.error("expression [" + expression + "]", e);
            e.printStackTrace();
            return null;
        }
    }

    private static Object parseExpression(String expression) throws OgnlException {
        try {
            Node node = expressionCache.get(expression);
            if (node == null) {
                node = new OgnlParser(new StringReader(expression)).topLevelExpression();
                expressionCache.put(expression, node);
            }
            return node;
        } catch (ParseException e) {
            throw new ExpressionSyntaxException(expression, e);
        } catch (TokenMgrError e) {
            throw new ExpressionSyntaxException(expression, e);
        }
    }
}
