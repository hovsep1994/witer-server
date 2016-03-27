package com.waiter.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hovsep on 3/27/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.waiter.server.api"})
public class ApiConfig extends WebMvcConfigurerAdapter {

}
