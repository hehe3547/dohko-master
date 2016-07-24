package com.dohko.app.boot;

import com.dohko.core.config.ServerConfig;
import com.dohko.core.grpc.server.RpcServerSupport;
import com.dohko.core.services.annotation.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;


@EnableAutoConfiguration
@ComponentScan(basePackages={"com.dohko.core.services","com.dohko.core.test","com.dohko.app.*.service"}, nameGenerator=BeanNameGenerator.class)
@EnableConfigurationProperties(ServerConfig.class)
public class NativeTestApplication extends SpringApplication {

	private static final Logger logger = Logger.getLogger(NativeTestApplication.class.getName());
	
	public static void testmain(String[] args) throws IOException, InterruptedException {
		SpringApplication app = new SpringApplication(NativeTestApplication.class);
		app.setWebEnvironment(false);
        ApplicationContext ctx = app.run(args);
        logger.info("Let's inspect the beans provided by Spring Boot:");
        
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
        System.out.println("server start success");
        server.blockUntilShutdown();
//        final RpcServer server = new RpcServer(port, ctx);
//		server.start();
//		server.blockUntilShutdown();
        
    }
	
	
}
