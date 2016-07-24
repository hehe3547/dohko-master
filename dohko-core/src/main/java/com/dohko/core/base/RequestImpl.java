package com.dohko.core.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestImpl implements Request {

	private final String tranceId;
	private String serviceName;
	private DataMap dataMap;
	private Holder holder;

	RequestImpl(String serviceName, DataMap dataMap, Holder holder) {
		this.serviceName = serviceName;
		this.dataMap = dataMap;
		this.holder = holder;
		if (holder != null) {
			tranceId = holder.getValue(Holder.Key.TRANCEID);
		} else {
			tranceId = UUID.randomUUID().toString();
		}
	}

	@Override
	public String getTranceId() {
		return this.tranceId;
	}

	@Override
	public String getService() {
		return serviceName;
	}

	public Request setService(String service) {
		this.serviceName = service;
		return this;
	}

	@Override
	public DataMap getDataMap() {
		return this.dataMap;
	}

	public Request setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}
	@Override
	public Holder getHolder() {
		return this.holder;
	}

	@Override
	public Request setHolder(Holder holder) {
		this.holder = holder;
		return this;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("service", this.serviceName);
		if (this.dataMap != null) {
			map.put("data", this.dataMap.toMap());
		}
		if (this.holder != null) {
			map.put("holder", this.holder.toMap());
		}
		return map;
	}

	@Override
	public Request clone() {
		return this;
	}

}
