package com.waiter.server.config;

import com.waiter.server.api.common.AuthenticationInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = Logger.getLogger(WebConfig.class);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("auth interceptor added. ");
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/heartbeat");
    }

}
