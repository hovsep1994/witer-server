package com.waiter.server.api.product.model.response;

import com.waiter.server.api.product.model.ProductPriceModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 8/19/16.
 */
public class ProductMenuModel {
    private Long id;
    private String name;
    private String description;
    private Double evaluation;
    private String image;
    private Set<TagModel> tags;
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

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    public Set<ProductPriceModel> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(Set<ProductPriceModel> productPrices) {
        this.productPrices = productPrices;
    }

    public static List<ProductMenuModel> convert(List<Product> products, Language language) {
        return products.stream()
                .map(product -> ProductMenuModel.convert(product, language))
                .collect(Collectors.toList());
    }

    public static ProductMenuModel convert(Product product, Language language) {
        ProductMenuModel productMenuModel = new ProductMenuModel();
        productMenuModel.setId(product.getId());
        productMenuModel.setName(product.getNameTranslationByLanguage(language).getText());
        productMenuModel.setDescription(product.getDescriptionTextByLanguage(language));
        productMenuModel.setEvaluation(product.getAverageRating());
        productMenuModel.setProductPrices(ProductPriceModel.convert(product.getProductPrices(), language));
        productMenuModel.setTags(TagModel.convertToModel(product.getTags()));
        return productMenuModel;
    }
}
