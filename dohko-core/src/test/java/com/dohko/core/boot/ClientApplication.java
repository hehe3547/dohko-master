package com.dohko.core.boot;

import com.dohko.core.base.DataMap;
import com.dohko.core.config.ClientConfig;
import com.dohko.core.config.WebConfig;
import com.dohko.core.services.Client;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EnableAutoConfiguration
@ComponentScan(basePackages={"com.dohko.core.grpc.client","com.dohko.core.web","com.dohko.app.*.web"})
@EnableConfigurationProperties({ClientConfig.class, WebConfig.class})
public class ClientApplication extends SpringApplication implements EmbeddedServletContainerCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientApplication.class.getName());
	
	public static void testmain(String[] args) throws IOException, InterruptedException {
		ApplicationContext ctx = SpringApplication.run(ClientApplication.class, args);
        
        logger.info("Let's inspect the beans provided by Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its
				// JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				System.err.println("*** server shut down");
			}
		});
        long startTime = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1; i ++) {
        	es.submit(new RequestService());
        }
       
        es.shutdown();
        if (es.awaitTermination(10000, TimeUnit.SECONDS)) {
        	 System.out.println("cost [" + (System.currentTimeMillis() - startTime) + "]ms");
        }
       // new Thread(new RequestService()).start();
    }
	
	static class RequestService implements Runnable {

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 1; i++) {
				DataMap resultInfo = Client.Builder.execute("mxb_simpleService", DataMap.Builder.create());
				LogUtils.info(resultInfo, "service [" + "mxb_simpleService" + "]", logger);
				logger.info("Greeting: " + resultInfo.getString("test"));
				
				Client.Builder.execute("mxb_simpleService", DataMap.Builder.create(), new Client.Listener() {
					@Override
					public void onCompleted(DataMap resultInfo) {
						LogUtils.info(resultInfo,  "service [" + "mxb_simpleService" + "]", logger);
						logger.info("async Greeting: " + resultInfo.getString("test"));
					}
				});
			}
			
			System.out.println("Thread [" + Thread.currentThread() + "] cost [" + (System.currentTimeMillis() - startTime) + "]ms");
		}
		
	}
//
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8083); //implements EmbeddedServletContainerCustomizer 
	}
}
