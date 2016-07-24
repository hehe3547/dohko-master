package com.dohko.core.services.annotation;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
/**
 * 自定义后成spring beanName
 * @author xiangbin
 *
 */
public class BeanNameGenerator extends AnnotationBeanNameGenerator{
	
	
	protected String determineBeanNameFromAnnotation(
			AnnotatedBeanDefinition annotatedDef) {
		String beanName = super.determineBeanNameFromAnnotation(annotatedDef);
		//没有查找到beanName时
		if (beanName == null) {
			AnnotationMetadata amd = annotatedDef.getMetadata();
			Set<String> types = amd.getAnnotationTypes();
			for (String type : types) {
				Map<String, Object> attributes = amd.getAnnotationAttributes(type);
				if (type.equals(AppService.class.getName())) {
					String value = (String) attributes.get("name");
					if (StringUtils.hasLength(value)) {
						if (beanName != null && !value.equals(beanName)) {
							throw new IllegalStateException("AppService Stereotype annotations suggest inconsistent " +
									"component names: '" + beanName + "' versus '" + value + "'");
						}
						beanName = value;
					}
				}
			}
		}
		
		return beanName;
	}
}
