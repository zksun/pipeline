package com.sun.pipeline.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * Created by zhikunsun on 2017/11/7.
 */
@Configuration()
@MapperScan("com.sun.pipeline.mybatis.dao")
public class MybatisConfig {
    @Value("classpath:mybatis/sql-map-config.xml")
    private Resource configLocation;

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @javax.annotation.Resource
    private DataSource dataSource;

    @Bean
    private DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


}
