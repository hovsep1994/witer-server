package com.waiter.server.api.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.category.model.CategoryModel;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hovsep on 3/5/16.
 */
public class ProductModel extends AbstractProductModel {

    @JsonProperty(value = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "category")
    private CategoryModel categoryModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public static ProductModel convert(Product product, Language language) {
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setDescription(product.getDescriptionByLanguage(language).getName());
        productModel.setDescription(product.getNameTranslationByLanguage(language).getName());
        productModel.setPrice(product.getPrice());
        productModel.setTagModels(TagModel.convertToModel(product.getTags()));
        return productModel;
    }

    public static List<ProductModel> convert(List<Product> products, Language language) {
        List<ProductModel> productModels = new ArrayList<>(products.size());
        products.stream().forEach(product -> {
            productModels.add(convert(product, language));
        });
        return productModels;
    }
}
