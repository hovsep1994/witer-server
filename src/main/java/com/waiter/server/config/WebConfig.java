package com.waiter.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author shahenpoghosyan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={ "com.waiter.server.api.*", "com.waiter.server.services.*.impl"})
public class WebConfig {
}
