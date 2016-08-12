package com.waiter.server.solr.core.repository.product;

import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
public interface ProductSolrRepositoryCustom {

    List<ProductDocument> findBySearchParams(String text);

}