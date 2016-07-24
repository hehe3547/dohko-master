package com.dohko.app.boot;

import com.dohko.core.config.ClientConfig;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.config.WebConfig;
import com.dohko.core.services.annotation.BeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by xiangbin on 2016/7/15.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.dohko.core.web","com.dohko.app.web","com.dohko.core.services","com.dohko.core.grpc.client", "com.dohko.app.*.service"}, nameGenerator=BeanNameGenerator.class)
@ImportResource("classpath:config/*-config.xml")
@EnableConfigurationProperties({ServerConfig.class, WebConfig.class, ClientConfig.class})
public class WebTestApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8088);
    }
}
