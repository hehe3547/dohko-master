package com.dohko.core.grpc.client;

import com.dohko.core.PlatConstants;
import com.dohko.core.base.DataMap;
import com.dohko.core.config.ClientConfig;
import com.dohko.core.grpc.support.RpcClient;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于直连服务,需要配置client.address(服务地址),client.services(地址对应的服务名)
 */
@Component("services_rpcClientSupport")
public class RpcClientSupport extends RpcClient {

	private Logger logger = LoggerFactory.getLogger(RpcClientSupport.class);

	@Autowired
	private ClientConfig clientConfig;

	@PostConstruct
	public void start() {
		LogUtils.info("config servers [" + clientConfig.getServers() + "]", logger);
		if (clientConfig.getServers() == null || clientConfig.getGroups() == null) {
			return;
		}


		String[] allServers = clientConfig.getServers().split(";");
		List<Referer> referer = new ArrayList<>();
		Map<String, Referer> connectCache = new HashMap<>();

		for (int i = 0; i < allServers.length; i++) {
			String groupName = allServers[i];
			String server = allServers[i];
			String[] serverInfo = allServers[i].split(":");
			if (serverInfo.length != 2) {
				LogUtils.error("config group [" + groupName + "] server [" + server + "] error", logger);
				continue;
			}
			LogUtils.info("init server [" + server +"] success ...", logger);
			String host = serverInfo[0];
			int port = Integer.parseInt(serverInfo[1]);
			LogUtils.info("connect host [" + host + "] port [" + port + "] server", logger);
			Referer connect = new RefererImpl(host, port);
			referer.add(connect);
			connectCache.put(server, connect);
		}
		String groups = clientConfig.getGroups();
		LogUtils.info("init services [" + groups +"] ", logger);
		String[] clientGroup = groups.split(";");
		Map<String, List<Referer>> serviceCache = new HashMap<>();
		for (int i = 0; i < clientGroup.length; i++) {
			serviceCache.put(clientGroup[i], referer);
		}
		DataMap clientDataMap = DataMap.Builder.create().put(PlatConstants.KEY_GROUP, groups);
		super.refresh(clientDataMap, serviceCache, connectCache);
		LogUtils.info("init group [" + groups + "] serviceCache [" + serviceCache + "] success ...", logger);
	}

	@Override
	protected String pattern(String serviceName) {
		if (serviceName.indexOf("_") > -1) {
			String groupName = serviceName.substring(0, serviceName.indexOf("_"));
			return groupName;
		}
		return super.pattern(serviceName);
	}

	@PreDestroy
	public void shutdown() throws InterruptedException {
		super.shutdown();
	}


	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}
}
