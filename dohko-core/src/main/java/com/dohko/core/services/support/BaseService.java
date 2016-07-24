package com.dohko.core.services.support;

import com.dohko.core.app.AbstractApp;
import com.dohko.core.base.*;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.services.Service;
import com.dohko.core.util.LogUtils;

abstract class BaseService extends AbstractApp implements Service {

	public ResultInfo execute(final DataMap dataMap, final Holder holder) {
		LogUtils.error( "not support execute(DataMap) method", logger);
		return PlatResultInfo.SUPPORT_METHOD;
	}

	public ResultInfo execute(final DataMap dataMap) {
		LogUtils.error( "not support execute(DataMap) method", logger);
		return PlatResultInfo.SUPPORT_METHOD;
	}

}
