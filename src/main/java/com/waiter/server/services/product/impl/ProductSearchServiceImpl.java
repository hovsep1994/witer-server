package com.waiter.server.services.product.impl;

import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.venue.model.Venue;
import com.waiter.server.solr.core.repository.category.CategorySolrDocument;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ProductDocument get(Long id) {
        notNull(id);
        return productSolrRepository.findByProductId(id);
    }

    @Override
    public void add(Long productId) {
        notNull(productId);
        final Product product = productService.getById(productId);
        for (Translation translation : product.getNameSet()) {
            final Language language = translation.getLanguage();
            final ProductDocument productDocument = new ProductDocument();
            setupProductFields(productDocument, product, language);
            setupCategoryFields(productDocument, product.getCategory(), language);
            //TODO Venue problem
//            productDocument.setVenueSolrDocument(getVenueSolrDocument(product.getCategory().getMenu().getCompany().get));
            productSolrRepository.save(productDocument);
        }
    }

    @Override
    public List<ProductDocument> findProductsStartingWith(String name) {
        return productSolrRepository.findByProductNameStartingWith(name);
    }

    private void setupProductFields(ProductDocument productDocument, Product product, Language language) {
        productDocument.setId(product.getId().toString() + language.toString());
        productDocument.setProductId(product.getId());
        productDocument.setLanguage(language.name());
        productDocument.setProductName(product.getNameTranslationByLanguage(language).getName());
        productDocument.setDescription(getTextFromTranslation(product.getDescriptionByLanguage(language)));
        productDocument.setEvaluation(product.getAverageRating());
        productDocument.setPrice(product.getPrice());
        productDocument.setGalleryId(product.getGallery().getId());
        productDocument.setMenuId(product.getCategory().getMenu().getId());
    }

    private void setupCategoryFields(ProductDocument productDocument, Category category, Language language) {
        productDocument.setCategoryId(category.getId());
        productDocument.setCategoryName(getTextFromTranslation(category.getNameTranslationByLanguage(language)));
        productDocument.setCategoryTags(Tag.toStrings(category.getTags()));
    }

    private VenueSolrDocument getVenueSolrDocument(Venue venue) {
        final VenueSolrDocument venueSolrDocument = new VenueSolrDocument();
        venueSolrDocument.setVenueId(venue.getId());
        venueSolrDocument.setName(venue.getName());
        venueSolrDocument.setCompanyId(venue.getCompany().getId());
        final Point point = new Point(venue.getLocation().getLatitude(), venue.getLocation().getLongitude());
        venueSolrDocument.setLocation(point);
        return venueSolrDocument;
    }

    private String getTextFromTranslation(Translation translation) {
        return translation == null ? null : translation.getName();
    }

}
