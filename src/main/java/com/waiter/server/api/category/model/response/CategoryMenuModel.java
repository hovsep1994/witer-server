package com.waiter.server.api.category.model.response;

import com.waiter.server.api.product.model.response.ProductClientModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.tag.model.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 8/19/16.
 */
public class CategoryMenuModel {
    private Long id;
    private String name;
    private Set<String> tags;
    private String image;
    private List<ProductClientModel> products;

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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<ProductClientModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductClientModel> products) {
        this.products = products;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<CategoryMenuModel> convert(List<Category> categories, Language language) {
        return categories.stream()
                .map(category -> CategoryMenuModel.convert(category, language))
                .collect(Collectors.toList());
    }

    public static CategoryMenuModel convert(Category category, Language language) {
        CategoryMenuModel categoryMenuModel = new CategoryMenuModel();
        categoryMenuModel.setName(category.getNameTranslationByLanguage(language).getText());
        categoryMenuModel.setId(category.getId());
        categoryMenuModel.setTags(category.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        categoryMenuModel.setProducts(ProductClientModel.convert(category.getProducts(), language));
        categoryMenuModel.setImage(ImageUrlGenerator.getUrl(EntityType.CATEGORY, category.getGallery()));
        return categoryMenuModel;
    }
}
