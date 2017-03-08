package com.waiter.server.api.search.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.product.model.ProductPriceModel;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
public class ProductSearchModel {


    @JsonProperty(value = "id")
    private Long id;

    private Set<ProductPriceModel> productPrices;

    private String description;

    private String name;

    private Set<String> tags;

    private String image;

    private VenueSearchModel venueModel;

    public Long getId() {
        return id;
    }

    public ProductSearchModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Set<ProductPriceModel> getProductPrices() {
        return productPrices;
    }

    public ProductSearchModel setProductPrices(Set<ProductPriceModel> productPrices) {
        this.productPrices = productPrices;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductSearchModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductSearchModel setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getTags() {
        return tags;
    }

    public ProductSearchModel setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductSearchModel setImage(String image) {
        this.image = image;
        return this;
    }

    public VenueSearchModel getVenueModel() {
        return venueModel;
    }

    public void setVenueModel(VenueSearchModel venueModel) {
        this.venueModel = venueModel;
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

        Venue venue = product.getCategory().getMenu().getVenues().get(0);
        productModel.setVenueModel(VenueSearchModel.convert(venue));
        return productModel;
    }

    public static List<ProductSearchModel> convert(Collection<Product> products, Language language) {
        return products.stream().map(product -> convert(product, language)).collect(Collectors.toList());
    }
}
