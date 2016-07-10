package com.waiter.server.config;

import com.waiter.server.web.common.WebAuthenticationInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author shahenpoghosyan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.waiter.server.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = Logger.getLogger(WebConfig.class);

    @Autowired
    private WebAuthenticationInterceptor webAuthenticationInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles/**")
                .addResourceLocations("/styles/");
        registry.addResourceHandler("/scripts/**")
                .addResourceLocations("/scripts/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("auth interceptor added. ");
        registry.addInterceptor(webAuthenticationInterceptor).addPathPatterns("/business/*");
    }

    @Bean
    public WebAuthenticationInterceptor authenticationInterceptor() {
        return new WebAuthenticationInterceptor();
    }
}
