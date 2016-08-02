package com.waiter.server.services.product.impl;

import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

/**
 * Created by hovsep on 8/2/16.
 */
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ProductSolrRepository productSolrRepository;

    @Autowired
    private ProductService productService;

    public ProductDocument get(String id) {
        notNull(id);
        return productSolrRepository.findOne(id);
    }

    public void add(String productId) {
        notNull(productId);
        final Product product = productService.getById(Long.valueOf(productId));
        for (Translation translation : product.getNameSet()) {
            final ProductDocument productDocument = new ProductDocument();
            productDocument.setId(product.getId().toString());
            productDocument.setLanguage(translation.getLanguage());
            productDocument.setName(translation.getName());
            productSolrRepository.save(productDocument);
        }
    }

}
