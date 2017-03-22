package com.waiter.server.api.search.model.response;

import com.waiter.server.api.product.model.ProductPriceModel;
import com.waiter.server.api.product.model.response.ProductClientModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.venue.model.Venue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
public class ProductSearchModel extends ProductClientModel {

    private ProductVenue venue;

    public ProductVenue getVenue() {
        return venue;
    }

    public void setVenue(ProductVenue venue) {
        this.venue = venue;
    }

    public static class ProductVenue {

        private String name;

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static ProductVenue convert(Venue venue) {
            ProductVenue productVenue = new ProductVenue();
            productVenue.setId(venue.getId());
            productVenue.setName(venue.getName());
            return productVenue;
        }
    }

    public static ProductSearchModel convert(Product product, Language language) {
        final ProductSearchModel productModel = new ProductSearchModel();
        productModel.setId(product.getId());
        productModel.setDescription(product.getDescriptionTextByLanguage(language));
        Translation translation = product.getNameTranslationByLanguage(language);
        if(translation == null) {
            product.getNameTranslationByLanguage(product.getCategory().getMenu().getMainLanguage());
        }
        if(translation == null) {
            throw new ServiceRuntimeException(ErrorCode.FAILD_TRANSLATION, "Valodikkkkkkk.  " + product.getNameSet().size());
        }
        productModel.setName(product.getNameTranslationByLanguage(language).getText());
        productModel.setProductPrices(ProductPriceModel.convert(product.getProductPrices(), language));
        productModel.setTags(product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        productModel.setImage(ImageUrlGenerator.getUrl(EntityType.PRODUCT, product.getGallery()));
        productModel.setRating(product.getAverageRating());

        Venue venue = product.getCategory().getMenu().getVenues().get(0);
        productModel.setVenue(ProductVenue.convert(venue));
        return productModel;
    }

    public static List<ProductSearchModel> convertToSearchModel(Collection<Product> products, Language language) {
        return products.stream().map(product -> convert(product, language)).collect(Collectors.toList());
    }
}
