package com.waiter.server.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author shahenpoghosyan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.waiter.server.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = Logger.getLogger(WebConfig.class);

//    @Bean
//    public AuthenticationInterceptor authenticationInterceptor() {
//        return new AuthenticationInterceptor();
//    }
//
//    @Autowired
//    private AuthenticationInterceptor authenticationInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        logger.info("auth interceptor added. ");
//        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/heartbeat");
//    }

}
