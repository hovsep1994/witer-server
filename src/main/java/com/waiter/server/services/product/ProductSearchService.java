package com.waiter.server.services.product;

import com.waiter.server.solr.core.repository.product.model.ProductDocument;

import java.util.List;

/**
 * Created by hovsep on 8/2/16.
 */
public interface ProductSearchService {

    ProductDocument get(Long id);

    void add(Long productId);

    List<ProductDocument> findProductsStartingWith(String name);
}
