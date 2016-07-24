package com.dohko.core.services.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.dohko.core.util.LogUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by xiangbin on 2016/6/22.
 */
@Configuration
@MapperScan("com.dohko.app.*.mapper")
public class MybatisComponent {

    private Logger logger = LoggerFactory.getLogger(MybatisComponent.class);
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private MybatisConfig mybatisConfig;
//
    @Bean(initMethod="init", destroyMethod="close")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceConfig.getUrl());
        dataSource.setUsername(dataSourceConfig.getUser());
        dataSource.setPassword(dataSourceConfig.getPassword());
        dataSource.setMaxActive(dataSourceConfig.getMaxActive());
        dataSource.setMinIdle(dataSourceConfig.getMinIdle());
        dataSource.setInitialSize(dataSourceConfig.getInitialSize());
        dataSource.setMaxWait(30000);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(600000);
        try {
            dataSource.setFilters("log4j");
        } catch (SQLException e) {
            LogUtils.errorException(e, logger);
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name="platform_txManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setObjectFactory(new DataMapObjectFactory());
        sessionFactory.setPlugins(new Interceptor[] {new ParamQueryInterceptor(), new ParamUpdateInterceptor(), new ResultQueryInterceptor()});
        String mapperLocations = "classpath*:/config/mybatis/*sql.xml";
        if (mybatisConfig.getMapperLocations() != null && !"".equals(mybatisConfig.getMapperLocations().trim())) {
            mapperLocations = mybatisConfig.getMapperLocations();
        }
        sessionFactory.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));
        return sessionFactory.getObject();
    }
}
