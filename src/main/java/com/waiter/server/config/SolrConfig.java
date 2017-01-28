package com.waiter.server.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;

import java.util.Properties;

/**
 * Created by hovsep on 7/31/16.
 */
@Configuration
//@EnableSolrRepositories(value = "com.waiter.server.solr.*", multicoreSupport = true)
@ComponentScan("com.waiter.server.solr.impl.*")
public class SolrConfig {

    @Autowired
    private Properties appProperties;

    @Autowired
    private HttpSolrClient solrClient;

    @Bean
    public HttpSolrClient solrClient() {
        return new HttpSolrClient(appProperties.getProperty("solr.base.url"));
    }

    @Bean
    public SolrOperations solrTemplate() {
        SolrTemplate solrTemplate = new SolrTemplate(solrClient);
//        solrTemplate.setSolrConverter(solrConverter());
        return solrTemplate;
    }

//    @Bean
//    public MappingSolrConverter solrConverter(){
//        Converter converter = StringToPointConverter.INSTANCE;
//        SimpleSolrMappingContext simpleSolrMappingContext = new SimpleSolrMappingContext();
//        MappingSolrConverter mappingSolrConverter = new MappingSolrConverter(simpleSolrMappingContext);
//        mappingSolrConverter.get(StringToPointConverter.INSTANCE);
//        return mappingSolrConverter;
//    }

}
