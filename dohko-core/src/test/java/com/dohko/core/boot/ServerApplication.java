package com.dohko.core.boot;

import com.dohko.core.base.DataMap;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.grpc.server.RpcServerSupport;
import com.dohko.core.services.annotation.BeanNameGenerator;
import com.dohko.core.services.service.ServiceExecInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.PropertyResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;


@EnableAutoConfiguration
@ComponentScan(basePackages={"com.dohko.core.services", "com.dohko.core.grpc.server","com.dohko.app.*.service"}, nameGenerator=BeanNameGenerator.class)
@EnableConfigurationProperties(ServerConfig.class)
//@PropertySources(@PropertySource("classpath:app/server.properties"))
public class ServerApplication extends SpringApplication {

	private static final Logger logger = Logger.getLogger(ServerApplication.class.getName());
	public static void testmain(String[] args) throws IOException, InterruptedException {
		SpringApplication app = new SpringApplication(ServerApplication.class);
		app.setWebEnvironment(false);
        //ApplicationContext ctx = SpringApplication.run(ServerApplication.class, args);
        ApplicationContext ctx = app.run(args);
        logger.info("Let's inspect the beans provided by Spring Boot:");
        System.out.println("AutoConfiguration should have wired up our stuff");
        System.out.println("Let's see if we are doge-worthy.dohko..");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        RpcServerSupport server = ctx.getBean(RpcServerSupport.class);
        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its
				// JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				System.err.println("*** server shut down");
			}
		});
        ServiceExecInfo client = ctx.getBean("core_serviceExecInfo", ServiceExecInfo.class);
        client.serviceInfo("aaa", DataMap.Builder.create());
        PropertyResolver resolver = ctx.getBean(PropertyResolver.class);
        System.out.println(resolver);
        System.out.println(resolver.getProperty("core.server.registry.sessionTimeout"));
        System.out.println("server start success");
        server.blockUntilShutdown();
//        final RpcServer server = new RpcServer(port, ctx);
//		server.start();
//		server.blockUntilShutdown();
        
    }
	
	
}
