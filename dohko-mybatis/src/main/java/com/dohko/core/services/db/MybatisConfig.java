package com.dohko.core.services.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiangbin on 2016/6/24.
 */
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class MybatisConfig {

    private String mapperLocations;
    private String configuration;

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

}
