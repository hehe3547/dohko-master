package com.dohko.core.config;

import com.dohko.core.util.NetUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 设置服务启动的端口号,服务注册的地址,当前服务的分组信息
 * Created by xiangbin on 2016/6/22.
 */
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

    private int port;

    private String group;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
