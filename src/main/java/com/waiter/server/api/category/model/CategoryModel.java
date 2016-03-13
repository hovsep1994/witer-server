package com.waiter.server.api.category.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.model.AbstractIdApiModel;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;

import java.util.List;
import java.util.Set;

/**
 * Created by hovsep on 3/5/16.
 */
public class CategoryModel extends AbstractIdApiModel {

    @JsonProperty("menuId")
    private Long menuId;

    @JsonProperty("nameTranslation")
    private NameTranslationModel nameTranslation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("products")
    private List<ProductModel> productModels;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "tags")
    private Set<TagModel> tags;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public NameTranslationModel getNameTranslation() {
        return nameTranslation;
    }

    public void setNameTranslation(NameTranslationModel nameTranslation) {
        this.nameTranslation = nameTranslation;
    }

    public List<ProductModel> getProducts() {
        return productModels;
    }

    public void setProducts(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    public static CategoryModel convert(Category category, Language language) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(category.getId());
        categoryModel.setMenuId(category.getMenu().getId());
        categoryModel.setTags(TagModel.convertToModel(category.getTags()));
        categoryModel.setNameTranslation(NameTranslationModel.convert(category.getNameTranslationByLanguage(language)));
        categoryModel.setProducts(null);
        return categoryModel;
    }
}
