package com.dohko.core.grpc.server;

import com.dohko.core.base.Request;
import com.dohko.core.base.Response;
import com.dohko.core.config.ServerConfig;
import com.dohko.core.grpc.RpcRequest;
import com.dohko.core.grpc.RpcResponse;
import com.dohko.core.grpc.RpcServiceGrpc;
import com.dohko.core.grpc.util.RpcDataUtils;
import com.dohko.core.services.Executor;
import com.dohko.core.services.Service;
import com.dohko.core.util.LogUtils;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;

@Component("services_rpcServerSupport")
public class RpcServerSupport extends RpcServiceGrpc.AbstractRpcService implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(RpcServerSupport.class);
	private Server server;
	private ApplicationContext appContext;
	@Autowired
	private ServerConfig serverConfig;

	@PostConstruct
	public void start() throws IOException, InterruptedException {
		int port = this.serverConfig.getPort();
		server = ServerBuilder.forPort(port).addService(this).build().start();
		logger.info("Server [" + serverConfig.getGroup() + "] started, listening on " + port);
		String[] services = appContext.getBeanNamesForType(Service.class);
		Arrays.sort(services);
		for (String beanName : services) {
			LogUtils.info("service [" + beanName + "]", logger);
		}
		// server.awaitTermination();
	}

	@Override
	public void execute(RpcRequest req, StreamObserver<RpcResponse> responseObserver) {
		Request request = RpcDataUtils.toRequest(req);
		Executor executor = appContext.getBean("services_simpleExecutor", Executor.class);
		//Message message = Message.Builder.create(request.getService(), request);
		Response response = executor.execute(request);
		responseObserver.onNext(RpcDataUtils.toRpcResponse(response));
		responseObserver.onCompleted();
	}

	@PreDestroy
	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	public void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}
}
