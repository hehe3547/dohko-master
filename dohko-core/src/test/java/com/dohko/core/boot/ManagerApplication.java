package com.dohko.core.boot;

import com.dohko.core.config.ClientConfig;
import com.dohko.core.config.RegistryConfig;
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

import java.io.IOException;
import java.util.Arrays;


@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={"com.dohko.core.web", "com.dohko.core.manager","com.dohko.app.*.web", "com.dohko.core.grpc.cluster","com.dohko.core.registry.support"})
@EnableConfigurationProperties({RegistryConfig.class, ClientConfig.class, WebConfig.class})
public class ManagerApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ManagerApplication.class);
    }
	
    public static void testmain(String[] args) throws IOException, InterruptedException {
        //SpringApplication sa = new SpringApplication(WebApplication.class, args);
        ApplicationContext ctx = SpringApplication.run(ManagerApplication.class, args);
        //ApplicationContext ctx = sa.run(args);
        logger.info("Let's inspect the beans provided b y Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
//        FilterBean bean = ctx.getBean("/aa/aabb/acc.htm", FilterBean.class);
//        System.out.println(bean.getService());
//        System.out.println(bean.getReqData());
//        System.out.println(bean.getReqData().getProps());
//        System.out.println(bean.getReqData().getRecords());

    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8090);
	}

}
