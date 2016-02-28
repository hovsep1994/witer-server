package com.waiter.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;

/**
 * @author shahenpoghosyan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.waiter.server.api",
        "com.waiter.server.services.*.impl",
        "com.waiter.server.externalclients"})
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
