package com.dohko.core.boot;

import com.dohko.core.base.DataMap;
import com.dohko.core.services.Client;
import com.dohko.core.services.annotation.BeanNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@EnableAutoConfiguration
//单独启web服务
//@ComponentScan(basePackages={"com.dohko.core.web","com.dohko.registry.support","com.dohko.app.*.web","com.dohko.core.grpc.cluster"}, nameGenerator=BeanNameGenerator.class)
//包括服务,不需要注册
@ComponentScan(basePackages={"com.dohko.core.grpc.server", "com.dohko.core.services","com.dohko.app.*.service", "com.dohko.core.web","com.dohko.core.manager","com.dohko.app.*.web", "com.dohko.core.grpc.client"}, nameGenerator=BeanNameGenerator.class)
@ImportResource({"classpath:resources/web/base-config.xml"})
@PropertySources({@PropertySource("classpath:resources/env/server.properties"),@PropertySource("classpath:resources/env/client.properties")})
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
//        FilterBean bean = ctx.getBean("/aa/aabb/acc.htm", FilterBean.class);
//        System.out.println(bean.getService());
//        System.out.println(bean.getReqData());
//        System.out.println(bean.getReqData().getProps());
//        System.out.println(bean.getReqData().getRecords());


        long startTime = System.currentTimeMillis();
        int max = 1;
        ExecutorService es = Executors.newFixedThreadPool(max);
        for (int i = 0; i < max; i++) {
            es.submit(new BeanMark());
        }
        es.shutdown();
        if (es.awaitTermination(300000, TimeUnit.SECONDS)) {

        }
        System.out.println("total cost [" + (System.currentTimeMillis() - startTime) +"]ms");

//        SpringApplication app = new SpringApplication(Startup.class);
//        app.setWebEnvironment(true);
//        app.setShowBanner(false);
//
//        Set<Object> set = new HashSet<Object>();
//        //set.add("classpath:applicationContext.xml");
//        app.setSources(set);
//        app.run(args);
    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8087);
	}

    static class BeanMark implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                DataMap retDs = Client.Builder.execute("core_serviceExecInfo", DataMap.Builder.create());
            }
            System.out.println("Thread [" + Thread.currentThread().getName() + "] cost [" + (System.currentTimeMillis() - startTime) +"]ms");
        }
    }
}
