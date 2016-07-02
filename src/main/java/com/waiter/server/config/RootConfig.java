package com.waiter.server.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.Properties;

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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Properties appProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource[] resources = new Resource[3];
        resources[0] = new ClassPathResource("menukit.properties");
        resources[1] = new FileSystemResource("file:${user.home}/.couchcoach/couchcoach.properties");
        resources[2] = new FileSystemResource("file:${user.home}/couchcoach.properties");
        propertiesFactoryBean.setLocations(resources);
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
