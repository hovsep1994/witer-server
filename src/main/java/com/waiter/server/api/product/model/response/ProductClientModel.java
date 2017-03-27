package com.waiter.server.api.product.model.response;

import com.waiter.server.api.product.model.ProductPriceModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 8/19/16.
 */
public class ProductClientModel {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String image;
    private Set<String> tags;
    private Set<ProductPriceModel> productPrices;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<ProductPriceModel> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(Set<ProductPriceModel> productPrices) {
        this.productPrices = productPrices;
    }

    public static List<ProductClientModel> convert(Collection<Product> products, Language language) {
        return products.stream()
                .map(product -> ProductClientModel.convert(product, language))
                .collect(Collectors.toList());
    }

    public static ProductClientModel convert(Product product, Language language) {
        ProductClientModel productClientModel = new ProductClientModel();
        productClientModel.setId(product.getId());
        productClientModel.setName(product.getNameTranslationByLanguage(language).getText());
        productClientModel.setDescription(product.getDescriptionTextByLanguage(language));
        productClientModel.setRating(product.getAverageRating());

        productClientModel.setProductPrices(ProductPriceModel
                .convert(product.getProductPrices(), language, product.getCategory().getMenu().getCurrency()));
        productClientModel.setTags(product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        productClientModel.setImage(ImageUrlGenerator.getUrl(EntityType.PRODUCT, product.getGallery()));
        return productClientModel;
    }
}
