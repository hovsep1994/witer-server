package com.waiter.server.services.product.impl;

import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.event.CategoryUpdateEvent;
import com.waiter.server.services.category.event.CategoryUpdateEventListener;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.event.ProductUpdateEvent;
import com.waiter.server.services.product.event.ProductUpdateEventListener;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.event.VenueLocationUpdateEvent;
import com.waiter.server.services.venue.event.VenueLocationUpdateEventListener;
import com.waiter.server.services.venue.model.Venue;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.product.model.ProductInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

/**
 * Created by hovsep on 8/2/16.
 */
@Service
public class ProductSearchServiceImpl implements ProductSearchService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSearchServiceImpl.class);

    @Autowired
    private ProductSolrRepository productSolrRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VenueService venueService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @Override
    public void afterPropertiesSet() {
        applicationEventBus.subscribe(productUpdateEventListener);
        applicationEventBus.subscribe(categoryUpdateEventListener);
        applicationEventBus.subscribe(venueLocationUpdateEventListener);
    }

    @Override
    public ProductDocument get(Long id) {
        notNull(id);
        return productSolrRepository.findOne(id.toString());
    }

    @Override
    public void addOrUpdate(Long productId) throws IOException {
        notNull(productId);
        LOGGER.debug("Storing product -{}", productId);
        final Product product = productService.getById(productId);
        productSolrRepository.save(ProductInputDocument.convert(product));
    }

    @Override
    public void addOrUpdateByCategoryId(Long categoryId) {
        notNull(categoryId);
        final Category category = categoryService.getById(categoryId);
        productSolrRepository.save(convertProductsToDocuments(category.getProducts()));
    }

    @Override
    public void addOrUpdateByVenueId(Long venueId) {
        notNull(venueId);
        final Venue venue = venueService.getById(venueId);
        final List<Product> products = new ArrayList<>();
        venue.getMenu().getCategories().forEach(category ->
                category.getProducts().forEach(products::add));

        productSolrRepository.save(convertProductsToDocuments(products));
    }

    @Override
    public List<ProductDocument> findProductsStartingWith(String name) {
        return productSolrRepository.findBySearchParams(name);
    }

    private static List<ProductInputDocument> convertProductsToDocuments(List<Product> products) {
        return products.stream()
                .map(ProductInputDocument::convert)
                .collect(Collectors.toList());
    }

    private final ProductUpdateEventListener productUpdateEventListener = new ProductUpdateEventListener() {
        @Override
        public void processProductUpdatedEvent(ProductUpdateEvent productUpdateEvent) {
            try {
                addOrUpdate(productUpdateEvent.getProductId());
            } catch (IOException e) {
                LOGGER.error("Exception updating solr doc. ", e);
            }
        }
    };

    private final CategoryUpdateEventListener categoryUpdateEventListener = new CategoryUpdateEventListener() {
        @Override
        public void processProductUpdatedEvent(CategoryUpdateEvent categoryUpdateEvent) {
            addOrUpdateByCategoryId(categoryUpdateEvent.getCategoryId());
        }
    };

    private final VenueLocationUpdateEventListener venueLocationUpdateEventListener = new VenueLocationUpdateEventListener() {
        @Override
        public void processVenueLocationUpdatedEvent(VenueLocationUpdateEvent venueLocationUpdateEvent) {
            LOGGER.info("Venue update: ");
            try {
                addOrUpdateByVenueId(venueLocationUpdateEvent.getVenueId());
            } catch (Exception e) {
                LOGGER.error("Exception updating products: ", e);
            }
        }
    };

}
