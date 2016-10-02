package com.waiter.server.background.conf;

import com.waiter.server.config.DataAccessConfig;
import com.waiter.server.config.RootConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shahenpoghosyan
 */
@Configuration
@Import({DataAccessConfig.class, RootConfig.class})
@ComponentScan({"com.waiter.server.background.services", "com.waiter.server.services"})
public class ApplicationConf {



}
