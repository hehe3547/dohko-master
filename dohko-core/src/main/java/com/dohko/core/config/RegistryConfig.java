package com.dohko.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by xiangbin on 2016/6/23.
 */
@ConfigurationProperties(prefix = "registry")
public class RegistryConfig {

    private String address;

    private int connTimeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }
}
