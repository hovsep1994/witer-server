package com.waiter.server.services.product.impl;

import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.event.CategoryUpdateEvent;
import com.waiter.server.services.category.event.CategoryUpdateEventListener;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductSearchParameters;
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
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void addOrUpdate(Product product) throws IOException {
        LOGGER.debug("Storing product into solr -{}", product.getId());
        productSolrRepository.save(ProductInputDocument.convert(product));
    }

    private void addOrUpdate(Long productId) throws IOException {
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
    public void addOrUpdateByVenueId(Venue venue) {
        notNull(venue);
        final List<Product> products = new ArrayList<>();
        venue.getMenu().getCategories().forEach(category ->
                category.getProducts().forEach(products::add));

        productSolrRepository.save(convertProductsToDocuments(products));
    }

    @Override
    public List<ProductDocument> findProductsStartingWith(String name) {
        return null;
    }

    public List<Product> findProducts(ProductSearchParameters parameters) {
        List<ProductDocument> productDocuments = productSolrRepository.findBySearchParams(parameters.getName(),
                new Point(parameters.getLongitude(), parameters.getLatitude()), parameters.getOffset(), parameters.getLimit());

        List<Long> productIds = productDocuments.stream().map(ProductDocument::getId).map(Long::valueOf).collect(Collectors.toList());
        List<Product> products =  productService.getAllByIds(productIds);
        products.sort((p1, p2) ->  productIds.indexOf(p1.getId()) - productIds.indexOf(p2.getId()));
        return orderProducts(products);
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
                addOrUpdateByVenueId(venueLocationUpdateEvent.getVenue());
            } catch (Exception e) {
                LOGGER.error("Exception updating products: ", e);
            }
        }
    };


    private List<Product> orderProducts(List<Product> products) {
        Map<Long, Double> map = new HashMap<>();
        List<RankedProduct> rankedProducts = new ArrayList<>();
        for (Product product : products) {
            Long venueId = product.getCategory().getMenu().getVenues().get(0).getId();
            if(map.get(venueId) == null) {
                map.put(venueId, 1.5d);
            } else {
                map.put(venueId, map.get(venueId) * 1.5d);
            }
            double ratingScore = product.getAverageRating() == 0 ? 0.0001 : product.getAverageRating();
            double score = ratingScore / map.get(venueId); // todo include distance in formula
            rankedProducts.add(new RankedProduct(product, score));
        }
        rankedProducts.sort((a, b) -> {
            if (b.rank - a.rank > 0) {
                return 1;
            }
            if (b.rank - a.rank < 0) {
                return -1;
            }
            return 0;
        });
        return rankedProducts.stream().map(x -> x.product).collect(Collectors.toList());
    }


    private static class RankedProduct {

        public RankedProduct(Product product, double rank) {
            this.product = product;
            this.rank = rank;
        }

        public Product product;
        public double rank;
    }

}
