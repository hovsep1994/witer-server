package com.waiter.server.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * Created by hovsep on 7/31/16.
 */
@Configuration
@EnableSolrRepositories(value = "com.waiter.server.solr.*")
public class SolrConfig {

    @Bean
    public SolrClient solrServer() {
        HttpSolrClient httpSolrClient = new HttpSolrClient("localhost:8080");
        httpSolrClient.getBaseURL();
        return httpSolrClient;
    }

    @Bean
    public SolrOperations solrTemplate() {
        return new SolrTemplate(solrServer());
    }

}
