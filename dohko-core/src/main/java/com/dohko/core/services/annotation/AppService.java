package com.dohko.core.services.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定义服务,当前类为服务类
 * @author xiangbin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface AppService {

	public enum ServiceType {
		ATOMIC,
		COMPOSITE,
		NATIVE,
		BATCH,
		METHOD;
	}

	public enum Param {
		HOLDER,
		DATAMAP
	}

	/**
	 * 设置服务的名字
	 */
	String value();
	/**
	 * 服务类型:原子服务，组合服务，批次服务
	 */
	//ServiceType type() default ServiceType.ATOMIC;
	/**
	 * 是否开启事务
	 */
	boolean transaction() default false;

	Param param() default Param.DATAMAP;
}
