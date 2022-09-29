package com.nrgroup.bucket.config;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class DataSourceConfig {
    
    @Bean
    @ConfigurationProperties("datasource.default-source")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("datasource.default-source")
    public DataSource defaultDataSource(@Autowired DataSourceProperties defaultDataSourceProperties) {
        return defaultDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public Jdbi defaultJdbi(@Autowired DataSource defaultDataSource) {
        TransactionAwareDataSourceProxy dataSourceProxy = new TransactionAwareDataSourceProxy(defaultDataSource);
        Jdbi jdbi = Jdbi.create(dataSourceProxy);
        jdbi.open().close();
        return jdbi;
    }

    // @Bean
    // @ConfigurationProperties("spring.datasource.second-source")
    // public DataSourceProperties secondDataSourceProperties() {
    // return new DataSourceProperties();
    // }

    // @Bean
    // public DataSource secondDataSource(@Autowired DataSourceProperties
    // secondDataSourceProperties) {
    // return secondDataSourceProperties.initializeDataSourceBuilder().build();
    // }

    // @Bean
    // public Jdbi secondJdbi(@Autowired DataSource secondDataSource) {
    // TransactionAwareDataSourceProxy dataSourceProxy = new
    // TransactionAwareDataSourceProxy(secondDataSource);
    // Jdbi jdbi = Jdbi.create(dataSourceProxy);
    // return jdbi;
    // }
}
