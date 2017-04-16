package com.waiter.server.api.category.model.response;

import com.waiter.server.api.product.model.response.ProductClientModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import java.util.Arrays;
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

    public static List<CategoryMenuModel> convert(List<Category> categories, Language language, boolean useProductImage) {
        return categories.stream()
                .map(category -> {
                    try {
                        return CategoryMenuModel.convert(category, language, useProductImage);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(x -> x != null)
                .collect(Collectors.toList());
    }

    public static List<CategoryMenuModel> convert(List<Category> categories, Language language) {
        return categories.stream()
                .map(category -> {
                    try {
                        return CategoryMenuModel.convert(category, language, false);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(x -> x != null)
                .collect(Collectors.toList());
    }

    public static CategoryMenuModel convert(Category category, Language language, boolean useProductImage) {
        CategoryMenuModel categoryMenuModel = new CategoryMenuModel();
        categoryMenuModel.setName(category.getNameTranslationByLanguages(Arrays.asList(language, Language.en)).getText());
        categoryMenuModel.setId(category.getId());
        categoryMenuModel.setTags(category.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        categoryMenuModel.setProducts(ProductClientModel.convert(category.getProducts(), language));

        categoryMenuModel.setImage(ImageUrlGenerator.getUrl(EntityType.CATEGORY, category.getGallery(), !useProductImage));
        if(categoryMenuModel.getImage() == null) {

            Product bestProduct = category.getProducts().stream().filter(p -> {
                String url = ImageUrlGenerator.getUrl(EntityType.PRODUCT, p.getGallery(), false);
                return url != null;
            }).max((p1, p2) -> {
                if (p2.getAverageRating() - p1.getAverageRating() > 0) return 1;
                if (p2.getAverageRating() - p1.getAverageRating() < 0) return -1;
                return 0;
            }).orElse(null);

            if (bestProduct != null) {
                categoryMenuModel.setImage(ImageUrlGenerator.getUrl(EntityType.CATEGORY, bestProduct.getGallery()));
            } else {
                categoryMenuModel.setImage(ImageUrlGenerator.getDefaultUrl(EntityType.CATEGORY));
            }
        }
        return categoryMenuModel;
    }

}
