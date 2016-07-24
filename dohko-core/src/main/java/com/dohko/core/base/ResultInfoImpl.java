package com.dohko.core.base;

import java.text.MessageFormat;

import com.dohko.core.PlatResultInfo;

public class ResultInfoImpl implements ResultInfo {

	private final String resultCode;
	private String resultMsg;
	
	ResultInfoImpl(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public boolean success() {
		return PlatResultInfo.SUCCESS.resultCode().equals(resultCode);
	}
	
	@Override
	public String resultCode() {
		return this.resultCode;
	}

	@Override
	public String resultMessage() {
		return this.resultMsg;
	}
	
	public ResultInfo formatResultMessage(Object[] arguments) {
		if (arguments != null && arguments.length > 0) {
			resultMsg = MessageFormat.format(resultMsg, arguments);
		}
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ResultInfoImpl)) {
			return false;
		}
		ResultInfoImpl impl = (ResultInfoImpl)obj;
		return this.resultCode.equals(impl.resultCode) && this.resultMsg.equals(this.resultMsg);
	}
}
