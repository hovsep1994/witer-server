package com.waiter.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by hovsep on 3/27/16.
 */
@Configuration
@EnableWebMvc
public class RootConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
