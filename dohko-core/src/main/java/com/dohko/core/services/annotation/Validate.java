package com.dohko.core.services.annotation;

import com.dohko.core.app.Validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

	
	Param[] value();
	
	public @interface Param {
		
		String value();

		Validator.Rule[] rules();

		String[] variable() default "";
	}
	
	public @interface Rule {
		
		Validator.Rule value();
		
		String variable() default "";
	}
}
