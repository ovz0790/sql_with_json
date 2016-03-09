package com.db.query.config;

import com.db.query.util.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by ovz on 28.12.15.
 */
@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseConfiguration.class);

    @Autowired
    public PropertiesReader propertiesReader;

    @Bean
    public DataSource dataSource(){
        String url = propertiesReader.readProperty("datasource.url");
        String username = propertiesReader.readProperty("datasource.username");
        String password = propertiesReader.readProperty("datasource.password");
        String driverClassName = propertiesReader.readProperty("datasource.driverClassName");

        LOG.debug("DataSource configuration:");
        LOG.debug("url: {}", url);
        LOG.debug("username: {}", username);
        LOG.debug("password: {}", (password.isEmpty() ? "empty" : "******"));

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        return ds;
    }



}
