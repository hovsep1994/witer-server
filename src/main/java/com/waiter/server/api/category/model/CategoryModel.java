package com.waiter.server.api.category.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.tag.model.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 3/5/16.
 */
public class CategoryModel extends AbstractCategoryModel {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty("menuId")
    private Long menuId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("products")
    private List<ProductModel> productModels;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "tags")
    private Set<String> tags;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static CategoryModel convert(Category category, Language language) {
        final CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(category.getId());
        categoryModel.setMenuId(category.getMenu().getId());
        categoryModel.setTags(TagModel.convertTagsToStrings(category.getTags()));
        categoryModel.setProductModels(null);
        categoryModel.setName(category.getNameTranslationByLanguage(language).getText());
        categoryModel.setLanguage(language);
        categoryModel.setImage(ImageUrlGenerator.getUrl(EntityType.CATEGORY, category.getGallery()));
        return categoryModel;
    }
}
