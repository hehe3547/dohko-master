package com.dohko.core.services.support;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.base.Request;
import com.dohko.core.base.Response;
import com.dohko.core.services.Client;
import com.dohko.core.services.Executor;
import com.dohko.core.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class BaseClient implements Client, ApplicationContextAware {

	protected static ApplicationContext appContext;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}
	@Override
	public final DataMap execute(String service, DataMap dataMap, Holder holder) {
		Request request = createRequest(service, dataMap, holder);
		Response response;
		if (isNativeService(request)) {
			response = nativeExecute(request);
		} else {
			response = execute(request);
		}
		DataMap resData = DataUtils.toDataMap(response);
		return resData;
	}

	@Override
	public final void execute(String service, DataMap dataMap, Holder holder, final Listener listener) {
		Request request = createRequest(service, dataMap, holder);
		if (isNativeService(request)) {
			Response response = nativeExecute(request);
			listener.onCompleted(DataUtils.toDataMap(response));
		} else {
			execute(request, new ResponseListener() {
				@Override
				public void onCompleted(Response response) {
					listener.onCompleted(DataUtils.toDataMap(response));
				}
			});
		}
	}
	
	private Request createRequest(String service, DataMap dataMap, Holder holder) {
		return Request.Builder.create(service, dataMap, holder);
	}
	
	protected abstract void execute(Request request, ResponseListener listener);
	
	protected abstract Response execute(Request request);
	private Response nativeExecute(Request request) {
		Executor executor = appContext.getBean(Executor.BEAN_ID, Executor.class);
		return executor.execute(request);
	}

	private boolean isNativeService(Request request) {
		String service = request.getService();
//		if (appContext.containsBean(service)) {
//			LogUtils.info("service [" + service + "] is native service", logger);
//			return true;
//		}
		return false;
	}

	public static class Builder {
		
		public static Client getClient() {
			return appContext.getBean(Client.class);
		}
	}
	
	public interface ResponseListener {
		
		void onCompleted(Response response);

	}
}
