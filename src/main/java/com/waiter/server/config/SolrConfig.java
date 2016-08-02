package com.waiter.server.config;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.io.IOException;

/**
 * Created by hovsep on 7/31/16.
 */
@Configuration
@EnableSolrRepositories(value = "com.waiter.server.solr.*")
public class SolrConfig {

    @Autowired
    private HttpSolrClient solrClient;

    @Bean
    public HttpSolrClient solrServer() throws IOException, SolrServerException {
        HttpSolrClient httpSolrClient = new HttpSolrClient("http://localhost:8983/solr/product");
        httpSolrClient.ping();
        return httpSolrClient;
    }

    @Bean
    public SolrOperations solrTemplate() {
        return new SolrTemplate(solrClient);
    }

}
