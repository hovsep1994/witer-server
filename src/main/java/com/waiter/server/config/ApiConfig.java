package com.waiter.server.config;

import com.waiter.server.api.common.ApiAuthenticationInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hovsep on 3/27/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.waiter.server.api"})
public class ApiConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = Logger.getLogger(ApiConfig.class);

    @Autowired
    private ApiAuthenticationInterceptor apiAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("auth interceptor added. ");
        registry.addInterceptor(apiAuthenticationInterceptor).addPathPatterns("/heartbeat");
    }

    @Bean
    public ApiAuthenticationInterceptor authenticationInterceptor() {
        return new ApiAuthenticationInterceptor();
    }


}
