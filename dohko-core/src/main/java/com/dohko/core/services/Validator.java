package com.dohko.core.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum Validator {

	REQUIRED("requried"),
	INT("int"),
	MIN("min"),
	MAX("max");
	
	private String name;
	private String param;
	private Set<Validator> validates = new HashSet<>();
	
	private Validator(String name) {
		this.name = name;
	}
	
	public static ValidateBuilder build() {
		return new ValidateBuilder();
	}
	
	public Validator append(Validator type) {
		validates.add(type);
		return this;
	}
	
	public Validator setParams(String param) {
		this.param = param;
		return this;
	}
	
	public String toValidate() {
		StringBuilder sb = new StringBuilder(this.name);
		if (this.param != null && !"".equals(this.param.trim())) {
			sb.append("(").append(this.param).append(")");
		}
		for (Validator type : validates) {
			sb.append(";").append(type.toValidate());
		}
		return sb.toString();
	}
	
	public static class ValidateBuilder {

		private Map<String, Validator> cache = new HashMap<>();

		public ValidateBuilder add(String fieldCode, Validator validateType) {
			cache.put(fieldCode, validateType);
			return this;
		}
		
		public String toValidate() {
			StringBuilder sb = new StringBuilder();
			Set<String> keySet = cache.keySet();
			for (String key : keySet) {
				sb.append(key).append(":").append(cache.get(key).toValidate());
			}
			return sb.toString();
		}
	}
}
