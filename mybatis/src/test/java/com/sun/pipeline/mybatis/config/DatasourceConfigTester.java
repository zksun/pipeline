package com.sun.pipeline.mybatis.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by zhikunsun on 2017/11/7.
 */
@Configuration
public class DatasourceConfigTester {
    @Value("${jdbc.driverClassName}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String user;
    @Value("${jdbc.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws Exception {

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(driverClass);
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);


        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setMaxPoolSize(100);
        comboPooledDataSource.setInitialPoolSize(10);

        comboPooledDataSource.setMaxIdleTime(1800);
        comboPooledDataSource.setIdleConnectionTestPeriod(60);

        comboPooledDataSource.setAcquireIncrement(3);
        comboPooledDataSource.setAcquireRetryAttempts(30);
        comboPooledDataSource.setAcquireRetryAttempts(30);

        comboPooledDataSource.setMaxStatements(1000);

        comboPooledDataSource.setBreakAfterAcquireFailure(true);
        comboPooledDataSource.setTestConnectionOnCheckin(false);

        return comboPooledDataSource;

    }
}
