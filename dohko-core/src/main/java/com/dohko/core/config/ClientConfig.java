package com.dohko.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 设置服务启动的端口号,服务注册的地址,当前服务的分组信息
 * Created by xiangbin on 2016/6/22.
 */
@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientConfig {

    private String servers;

    //直连时配置的服务名
    private String services;

    private String groups;

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
