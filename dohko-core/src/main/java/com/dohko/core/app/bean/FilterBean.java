package com.dohko.core.app.bean;

public class FilterBean {

	private String service;
	private boolean result;
	private MapBean request;
	private MapBean response;
	private ValidateBean validate;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

	public MapBean getRequest() {
		return request;
	}

	public void setRequest(MapBean request) {
		this.request = request;
	}

	public MapBean getResponse() {
		return response;
	}

	public void setResponse(MapBean response) {
		this.response = response;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public ValidateBean getValidate() {
		return validate;
	}

	public void setValidate(ValidateBean validate) {
		this.validate = validate;
	}
}
