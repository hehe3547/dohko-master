package com.dohko.core.base;

import com.dohko.core.PlatResultInfo;

import java.util.HashMap;
import java.util.Map;

public class ResponseImpl implements Response {

	private final boolean success;
	private String serviceName;
	private final String returnCode;
	private final String returnMessage;
	private DataMap dataMap;
	private final String tranceId;
	
	ResponseImpl(String tranceId, String returnCode, String returnMessage) {
		if (PlatResultInfo.SUCCESS.resultCode().equals(returnCode)) {
			this.success = true;
		} else {
			this.success = false;
		}
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.tranceId = tranceId;
	}

	ResponseImpl(String tranceId,ResultInfo resultInfo) {
		this(tranceId, resultInfo.resultCode(), resultInfo.resultMessage());
		if (resultInfo.success()) {
			if (resultInfo instanceof DataMap) {
				this.dataMap = (DataMap)resultInfo;
			} else if (resultInfo instanceof DataList) {
				this.dataMap = DataMap.Builder.create();
				this.dataMap.put(DataMap.DEFAULT_DATA_LIST, (DataList)resultInfo);
			}
		}
	}
	
	public String getService() {
		return this.serviceName;
	}

	public Response setService(String serviceName) {
		this.serviceName = serviceName;
		return this;
	}

	@Override
	public boolean success() {
		return this.success;
	}

	@Override
	public String getReturnCode() {
		return this.returnCode;
	}

	@Override
	public String getReturnMessage() {
		return this.returnMessage;
	}

	@Override
	public DataMap getDataMap() {
		return this.dataMap;
	}

	@Override
	public Response setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("success", this.success);
		map.put("returnCode", this.returnCode);
		map.put("returnMessage", this.returnMessage);
		if (tranceId != null) {
			map.put("tranceId", this.tranceId);
		}
		if (this.dataMap != null) {
			map.put("data", this.dataMap.toMap());
		}
		return map;
	}

	@Override
	public String getTranceId() {
		return this.tranceId;
	}
}
