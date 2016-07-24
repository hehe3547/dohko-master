package com.dohko.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by xiangbin on 2016/6/25.
 */
@Configuration
public class BaseMVCConfiguration extends WebMvcConfigurerAdapter {

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> messageConverter : converters) {
            System.out.println("message converter " + messageConverter); //2
        }
    }

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ResultMessageConverter resultConverter = new ResultMessageConverter();
        converters.add(0, resultConverter);
        for (HttpMessageConverter<?> messageConverter : converters) {
            System.out.println("message converter " + messageConverter); //2
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseInterceptor());
        super.addInterceptors(registry);
    }
}
