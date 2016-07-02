package com.waiter.server.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles/**")
                .addResourceLocations("/styles/");
        registry.addResourceHandler("/scripts/**")
                .addResourceLocations("/scripts/");
    }
}
