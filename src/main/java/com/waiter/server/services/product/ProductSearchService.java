package com.waiter.server.services.product;

import com.waiter.server.solr.core.repository.product.model.ProductDocument;

/**
 * Created by hovsep on 8/2/16.
 */
public interface ProductSearchService {

    ProductDocument get(String id);

    void add(String productId);
}
