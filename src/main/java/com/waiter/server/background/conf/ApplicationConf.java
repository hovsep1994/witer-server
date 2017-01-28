package com.waiter.server.background.conf;

import com.waiter.server.config.DataAccessConfig;
import com.waiter.server.config.RootConfig;
import com.waiter.server.config.SolrConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shahenpoghosyan
 */
@Configuration
@Import({DataAccessConfig.class, RootConfig.class, SolrConfig.class})
@ComponentScan(value = {
        "com.waiter.server.background.importer",
        "com.waiter.server.services",
        "com.waiter.server.externalclients"})
public class ApplicationConf {




}
