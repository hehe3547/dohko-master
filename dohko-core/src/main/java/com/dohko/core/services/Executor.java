package com.dohko.core.services;

import com.dohko.core.base.Request;
import com.dohko.core.base.Response;

public interface Executor {
	
	String BEAN_ID = "services_simpleExecutor";

	Response execute(Request request);
}
