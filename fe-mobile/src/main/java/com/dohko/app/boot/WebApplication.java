package com.dohko.app.boot;

import com.dohko.core.config.ClientConfig;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.config.WebConfig;
import com.dohko.core.grpc.server.RpcServerSupport;
import com.dohko.core.services.annotation.BeanNameGenerator;
import com.dohko.core.services.db.MybatisConfig;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;
import java.util.Arrays;


//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
//@ComponentScan(basePackages={"com.dohko.core.web","com.dohko.app.web", "com.dohko.core.grpc.client"})
//@ImportResource("classpath:config/**/*-config.xml")
//@EnableConfigurationProperties({ClientConfig.class, WebConfig.class})

@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, SpringDataWebAutoConfiguration.class})
@ComponentScan(basePackages={
        "com.dohko.core.grpc.server","com.dohko.core.services",
        "com.dohko.app.*.service", "com.dohko.core.web","com.dohko.app.web",
        "com.dohko.core.grpc.client"}, nameGenerator=BeanNameGenerator.class)
@ImportResource("classpath:config/**/*-config.xml")
@EnableConfigurationProperties({ServerConfig.class, WebConfig.class, ClientConfig.class, MybatisConfig.class})
public class WebApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }
	
    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);
        logger.info("Let's inspect the beans provided b y Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        LogUtils.info("start grpc server ....", logger);
        RpcServerSupport server = ctx.getBean(RpcServerSupport.class);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("*** shutting down gRPC server since JVM is shutting down");
                logger.info("*** server shut down");
            }
        });
        LogUtils.info("start grpc server successs cost [" + (System.currentTimeMillis() - startTime) + "]ms", logger);
        server.blockUntilShutdown();
        System.out.println("total cost [" + (System.currentTimeMillis() - startTime) +"]ms success ");
    }

	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8090);
	}

}
