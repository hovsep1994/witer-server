package com.waiter.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author shahenpoghosyan
 */
@Configuration
@ComponentScan(basePackages={ "com.waiter.server.api", "com.waiter.server.services"})
public class WebConfig {
}
