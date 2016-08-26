package com.waiter.server.config;

import com.waiter.server.api.common.ApiAuthenticationInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
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
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(apiAuthenticationInterceptor);
        interceptorRegistration(interceptorRegistration, "venue");
        interceptorRegistration(interceptorRegistration, "categories");
        interceptorRegistration(interceptorRegistration, "products");
        interceptorRegistration(interceptorRegistration, "menus");
        interceptorRegistration(interceptorRegistration, "heartbeat");
    }

    private static void interceptorRegistration(InterceptorRegistration interceptorRegistration, String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(path);
        interceptorRegistration.addPathPatterns(builder.toString());
        builder.append("/");
        interceptorRegistration.addPathPatterns(builder.toString());
        builder.append("/*");
        interceptorRegistration.addPathPatterns(builder.toString());
        builder.append("/**");
        interceptorRegistration.addPathPatterns(builder.toString());
    }

    @Bean
    public ApiAuthenticationInterceptor authenticationInterceptor() {
        return new ApiAuthenticationInterceptor();
    }


}
