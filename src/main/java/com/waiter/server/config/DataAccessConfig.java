package com.waiter.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author shahenpoghosyan
 */
@Configuration
@ComponentScan(basePackages="com.waiter.server.repository.*")
public class DataAccessConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource createJDBCDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://localhost/WAITER");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplateObject() {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
