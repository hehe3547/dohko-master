package com.dohko.core.util;

import com.dohko.core.base.*;
import org.slf4j.Logger;


public final class LogUtils {

	enum Level {
		DEBUG,
		INFO,
		ERROR,
		WARN;
	}

	public static void debug(String message, Logger logger) {
		log(Level.DEBUG, message, logger);
	}

	public static void info(String message, Logger logger) {
		log(Level.INFO, message, logger);
	}

	public static void warn(String message, Logger logger) {
		log(Level.WARN, message, logger);
	}

	public static void error(String message, Logger logger) {
		log(Level.ERROR, message, logger);
	}

	private static void log(Level level, String message, Logger logger) {
		if (level.equals(Level.ERROR) && logger.isErrorEnabled()) {
			logger.error(message);
		} else if (level.equals(Level.INFO) && logger.isInfoEnabled()) {
			logger.info(message);
		} else if (level.equals(Level.WARN) && logger.isWarnEnabled()) {
			logger.warn(message);
		} else if (level.equals(Level.DEBUG) && logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	public static void debug(Request request, Logger logger) {
		log(Level.DEBUG, request, null, logger);
	}

	public static void info(Request request, Logger logger) {
		log(Level.INFO, request, null, logger);
	}

	public static void warn(Request request, Logger logger) {
		log(Level.WARN, request, null, logger);
	}

	public static void error(Request request,Logger logger) {
		log(Level.ERROR, request, null, logger);
	}

	public static void debug(Request request, String message, Logger logger) {
		log(Level.DEBUG, request, message, logger);
	}

	public static void info(Request request, String message, Logger logger) {
		log(Level.INFO, request, message, logger);
	}

	public static void warn(Request request, String message, Logger logger) {
		log(Level.WARN, request, message, logger);
	}

	public static void error(Request request, String message, Logger logger) {
		log(Level.ERROR, request, message, logger);
	}
	
	private static void log(Level level, Request request, String message, Logger logger) {
		if (level.equals(Level.ERROR) && logger.isErrorEnabled()) {
			logger.error(toString(request, message));
		} else if (level.equals(Level.INFO) && logger.isInfoEnabled()) {
			logger.info(toString(request, message));
		} else if (level.equals(Level.WARN) && logger.isWarnEnabled()) {
			logger.warn(toString(request, message));
		} else if (level.equals(Level.DEBUG) && logger.isDebugEnabled()) {
			logger.debug(toString(request, message));
		}
	}

	private static String toString(Request request, String message) {
		if (message != null && !message.equals("")) {
			return message + " [" + DataUtils.request2json(request) + "]";
		} else {
			return DataUtils.request2json(request);
		}
	}

	public static void debug(Response response, Logger logger) {
		log(Level.DEBUG, response, null, logger);
	}

	public static void info(Response response, Logger logger) {
		log(Level.INFO, response, null, logger);
	}

	public static void warn(Response response, Logger logger) {
		log(Level.WARN, response, null, logger);
	}

	public static void error(Response response, Logger logger) {
		log(Level.ERROR, response, null, logger);
	}

	public static void debug(Response response, String message, Logger logger) {
		log(Level.DEBUG, response, message, logger);
	}

	public static void info(Response response, String message, Logger logger) {
		log(Level.INFO, response, message, logger);
	}

	public static void warn(Response response, String message, Logger logger) {
		log(Level.WARN, response, message, logger);
	}

	public static void error(Response response, String message, Logger logger) {
		log(Level.ERROR, response, message, logger);
	}
	
	private static void log(Level level, Response response, String message, Logger logger) {
		if (level.equals(Level.ERROR) && logger.isErrorEnabled()) {
			logger.error(toString(response, message));
		} else if (level.equals(Level.INFO) && logger.isInfoEnabled()) {
			logger.info(toString(response, message));
		} else if (level.equals(Level.WARN) && logger.isWarnEnabled()) {
			logger.warn(toString(response, message));
		} else if (level.equals(Level.DEBUG) && logger.isDebugEnabled()) {
			logger.debug(toString(response, message));
		}
	}

	private static String toString(Response response, String message) {
		if (message != null && !message.equals("")) {
			return message + " [" + DataUtils.response2json(response) + "]";
		} else {
			return DataUtils.response2json(response);
		}
	}

	public static void debug(ResultInfo resultInfo, Logger logger) {
		log(Level.DEBUG, resultInfo, null, logger);
	}

	public static void info(ResultInfo resultInfo, Logger logger) {
		log(Level.INFO, resultInfo, null, logger);
	}

	public static void warn(ResultInfo resultInfo, Logger logger) {
		log(Level.WARN, resultInfo, null, logger);
	}

	public static void error(ResultInfo resultInfo, Logger logger) {
		log(Level.ERROR, resultInfo, null, logger);
	}

	public static void debug(ResultInfo resultInfo, String message, Logger logger) {
		log(Level.DEBUG, resultInfo, message, logger);
	}

	public static void info(ResultInfo resultInfo, String message, Logger logger) {
		log(Level.INFO, resultInfo, message, logger);
	}

	public static void warn(ResultInfo resultInfo, String message, Logger logger) {
		log(Level.WARN, resultInfo, message, logger);
	}

	public static void error(ResultInfo resultInfo, String message, Logger logger) {
		log(Level.ERROR, resultInfo, message, logger);
	}

	public static void info(Holder holder, String message, Logger logger) {
		log(Level.INFO, message, logger);
	}

	public static void warn(Holder holder, String message, Logger logger) {
		log(Level.WARN, message, logger);
	}

	public static void error(Holder holder, String message, Logger logger) {
		log(Level.ERROR, message, logger);
	}

	public static void debug(Holder holder, String message, Logger logger) {
		log(Level.ERROR, message, logger);
	}
	
	private static void log(Level level, ResultInfo resultInfo, String message, Logger logger) {
		if (level.equals(Level.ERROR) && logger.isErrorEnabled()) {
			logger.error(toString(resultInfo, message));
		} else if (level.equals(Level.INFO) && logger.isInfoEnabled()) {
			logger.info(toString(resultInfo, message));
		} else if (level.equals(Level.WARN) && logger.isWarnEnabled()) {
			logger.warn(toString(resultInfo, message));
		} else if (level.equals(Level.DEBUG) && logger.isDebugEnabled()) {
			logger.debug(toString(resultInfo, message));
		}
	}

	private static String toString(ResultInfo resultInfo, String message) {
		if (message != null && !message.equals("")) {
			return message + " [" + DataUtils.resultInfo2json(resultInfo) + "]";
		} else {
			return DataUtils.resultInfo2json(resultInfo);
		}
	}

	
	public static void errorException(Throwable e, Logger logger) {
		e.printStackTrace();
		logger.error("system error", e);
	}

	public static void debug(Request request, Response response, Logger logger) {
		log(Level.DEBUG, request, response, null, logger);
	}

	public static void info(Request request, Response response, Logger logger) {
		log(Level.INFO, request, response, null, logger);
	}

	public static void warn(Request request, Response response, Logger logger) {
		log(Level.WARN, request, response, null, logger);
	}

	public static void error(Request request, Response response, Logger logger) {
		log(Level.ERROR, request, response, null, logger);
	}

	public static void debug(Request request, Response response, String message, Logger logger) {
		log(Level.DEBUG, request, response, message, logger);
	}

	public static void info(Request request, Response response, String message, Logger logger) {
		log(Level.INFO, request, response, message, logger);
	}

	public static void warn(Request request, Response response, String message, Logger logger) {
		log(Level.WARN, request, response, message, logger);
	}

	public static void error(Request request, Response response, String message, Logger logger) {
		log(Level.ERROR, request, response, message, logger);
	}
	
	
	private static void log(Level level, Request request, Response response, String message, Logger logger) {
		if (level.equals(Level.ERROR) && logger.isErrorEnabled()) {
			logger.error(message);
			logger.error("request [" + toString(request, null) + "]");
			logger.error("response [" + toString(response, null) + "]");
		} else if (level.equals(Level.INFO) && logger.isInfoEnabled()) {
			logger.info(message);
			logger.info("request [" + toString(request, null) + "]");
			logger.info("response [" + toString(response, null) + "]");
		} else if (level.equals(Level.WARN) && logger.isWarnEnabled()) {
			logger.warn(message);
			logger.warn("request [" + toString(request, null) + "]");
			logger.warn("response [" + toString(response, null) + "]");
		} else if (level.equals(Level.DEBUG) && logger.isDebugEnabled()) {
			logger.debug(message);
			logger.debug("request [" + toString(request, null) + "]");
			logger.debug("response [" + toString(response, null) + "]");
		}
	}

	private static String toString(Request request, Response response, String message) {
		if (message != null && !message.equals("")) {
			return message + " request [" + DataUtils.request2json(request) + "] response [" + DataUtils.response2json(response) + "]";
		} else {
			return "request [" + DataUtils.request2json(request) + "] response [" + DataUtils.response2json(response) + "]";
		}
	}
}
