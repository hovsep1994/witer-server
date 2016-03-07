package com.waiter.server.services.product.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.model.EntityType;
import com.waiter.server.services.name.model.NameTranslation;
import com.waiter.server.services.name.model.TranslationType;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public class AddProductDto extends AbstractDtoModel<Product> {

    private String name;
    private Language language;
    private Set<Tag> tags;
    private String description;
    private Double price;

    @Override
    public void convertToEntityModel(Product product) {
        NameTranslation nameTranslation = new NameTranslation();
        nameTranslation.setName(getName());
        nameTranslation.setLanguage(getLanguage());
        nameTranslation.setEntityType(EntityType.PRODUCT);
        nameTranslation.setTranslationType(TranslationType.MAIN);
        product.getNameTranslations().add(nameTranslation);
        if (tags != null) {
            product.getTags().addAll(tags);
        }
        product.setDescription(getDescription());
        product.setPrice(getPrice());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
