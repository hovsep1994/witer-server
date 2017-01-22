package com.waiter.server.services.product;

import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * Created by hovsep on 8/2/16.
 */
public interface ProductSearchService {

    ProductDocument get(Long id);

    void addOrUpdate(Long productId) throws IOException, SolrServerException;

    void addOrUpdateByCategoryId(Long categoryId);

    void addOrUpdateByVenueId(Long venueId);

    List<ProductDocument> findProductsStartingWith(String name);


}
