package com.waiter.server.api.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.category.model.CategoryModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 3/5/16.
 */
public class ProductModel {

    @JsonProperty(value = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "category")
    private CategoryModel categoryModel;

    private Set<ProductPriceModel> productPrices;

    private String description;

    private String name;

    private Set<TagModel> tagModels;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(Set<TagModel> tagModels) {
        this.tagModels = tagModels;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<ProductPriceModel> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(Set<ProductPriceModel> productPrices) {
        this.productPrices = productPrices;
    }

    public static ProductModel convert(Product product, Language language) {
        final ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setDescription(product.getDescriptionTextByLanguage(language));
        productModel.setName(product.getNameTranslationByLanguage(language).getText());
        productModel.setProductPrices(ProductPriceModel.convert(product.getProductPrices(), language));
        productModel.setTagModels(TagModel.convertToModel(product.getTags()));
        productModel.setImage(ImageUrlGenerator.getUrl(EntityType.PRODUCT, product.getGallery().getGalleryImages()));
        return productModel;
    }

    public static List<ProductModel> convert(Collection<Product> products, Language language) {
        return products.stream().map(product -> convert(product, language)).collect(Collectors.toList());
    }
}
