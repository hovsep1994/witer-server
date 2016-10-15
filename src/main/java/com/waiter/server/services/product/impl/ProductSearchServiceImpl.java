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
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.event.VenueLocationUpdateEvent;
import com.waiter.server.services.venue.event.VenueLocationUpdateEventListener;
import com.waiter.server.services.venue.model.Venue;
import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        final ProductDocument productDocument = convertProductToDocument(product);
        productSolrRepository.save(productDocument);
        LOGGER.debug("Product document -{} successfully stored", productDocument);
    }

    @Override
    public void addOrUpdateByCategoryId(Long categoryId) {
        notNull(categoryId);
        final Category category = categoryService.getById(categoryId);
        final List<ProductDocument> productDocuments = convertProductsToDocuments(category.getProducts());
        productSolrRepository.save(productDocuments);
    }

    @Override
    public void addOrUpdateByVenueId(Long venueId) {
        notNull(venueId);
        final Venue venue = venueService.getById(venueId);
        final List<Product> products = new ArrayList<>();
        venue.getMenu().getCategories().forEach(category ->
                category.getProducts().forEach(products::add));
        final List<ProductDocument> productDocuments = convertProductsToDocuments(products);
        productSolrRepository.save(productDocuments);
    }

//    @Override
//    public List<ProductDocument> findProductsStartingWith(String name) {
//        return productSolrRepository.findByProductNameStartingWith(name);
//    }

    private static List<ProductDocument> convertProductsToDocuments(List<Product> products) {
        return products.stream()
                .map(ProductSearchServiceImpl::convertProductToDocument)
                .collect(Collectors.toList());
    }

    private static ProductDocument convertProductToDocument(Product product) {
        final ProductDocument productDocument = new ProductDocument();
        //product fields
        productDocument.setId(product.getId().toString());
        productDocument.setProductNameTranslations(Translation.getListOfTexts(product.getNameSet()));
        productDocument.setDescriptionTranslations(Translation.getListOfTexts(product.getDescriptionSet()));
        productDocument.setEvaluation(product.getAverageRating());
//        productDocument.setPrice(product.get());
        productDocument.setGalleryId(product.getGallery().getId());
        productDocument.setMenuId(product.getCategory().getMenu().getId());
        //category fields
        final Category category = product.getCategory();
        productDocument.setCategoryId(category.getId());
        productDocument.setCategoryNameTranslations(Translation.getListOfTexts(category.getNameSet()));
        productDocument.setCategoryTags(Tag.toStringSet(category.getTags()));
        // venue fields
        final Set<SolrLocation> points = product.getCategory().getMenu().getCompany().getVenues().stream()
                .map(venue -> new SolrLocation(venue.getLocation().getLatitude(), venue.getLocation().getLongitude()))
                .collect(Collectors.toSet());
        productDocument.setLocations(points);
        // company fields
        productDocument.setCompanyId(product.getCategory().getMenu().getCompany().getId());
        return productDocument;
    }

    private final ProductUpdateEventListener productUpdateEventListener = new ProductUpdateEventListener() {
        @Override
        public void processProductUpdatedEvent(ProductUpdateEvent productUpdateEvent) {
            try {
                addOrUpdate(productUpdateEvent.getProductId());
            } catch (IOException e) {
                LOGGER.error("Exception updating solr doc. ");
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
            addOrUpdateByVenueId(venueLocationUpdateEvent.getVenueId());
        }
    };

}
