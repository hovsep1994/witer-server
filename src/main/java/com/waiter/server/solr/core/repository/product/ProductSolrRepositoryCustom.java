package com.waiter.server.solr.core.repository.product;

import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.product.model.ProductInputDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
public interface ProductSolrRepositoryCustom {

    List<ProductDocument> findBySearchParams(String text);

    void save(ProductInputDocument doc) throws IOException;

    void save(Collection<ProductInputDocument> docs);

    ProductDocument findOne(String id);

}
