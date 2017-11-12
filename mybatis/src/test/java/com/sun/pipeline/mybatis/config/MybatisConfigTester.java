package com.sun.pipeline.mybatis.config;

import org.mybatis.spring.SqlSessionFactoryBean;
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
public class MybatisConfigTester {
    @Value("classpath:test/mybatis/sql-map-config.xml")
    private Resource configLocation;

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @javax.annotation.Resource
    private DataSource dataSource;

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation(configLocation);
        sessionFactory.setTypeAliasesPackage("com.sun.pipeline.mybatis.domain;");
        //IMPORT: 约定sqlmap路径为resources/mybatis/slqmap/,spring会根据正则匹配所有xml文件
        Resource[] mappingFiles = resolver.getResources("classpath*:test/mybatis/sqlmap/*DAO.xml");
        sessionFactory.setMapperLocations(mappingFiles);
        return sessionFactory;
    }


}
