package com.dohko.core.test;

import com.dohko.core.config.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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


@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//单独启web服务
//@ComponentScan(basePackages={"com.dohko.core.web","com.dohko.registry.support","com.dohko.app.*.web","com.dohko.core.grpc.cluster"}, nameGenerator=BeanNameGenerator.class)
//包括服务,不需要注册
@ComponentScan(basePackages={"com.dohko.core.web","com.dohko.app.web"})
@ImportResource("classpath:config/base-config.xml")
@EnableConfigurationProperties({WebConfig.class})
public class WebApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }
	
    public static void testmain(String[] args) throws IOException, InterruptedException {
        //SpringApplication sa = new SpringApplication(WebApplication.class, args);
        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);
        //ApplicationContext ctx = sa.run(args);
        logger.info("Let's inspect the beans provided b y Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        long startTime = System.currentTimeMillis();
        System.out.println("total cost [" + (System.currentTimeMillis() - startTime) +"]ms");

    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8088);
	}

}
