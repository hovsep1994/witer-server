package com.waiter.server.services.product.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public class ProductDto extends AbstractDtoModel<Product> {

    private Set<String> tags;
    private Set<ProductPriceDto> productPriceDtos;
    private Boolean isAvailable;
    private String name;
    private String description;
    private Language language;

    @Override
    public void updateProperties(Product product) {
        product.setAvailable(isAvailable == null ? true : isAvailable);
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<ProductPriceDto> getProductPriceDtos() {
        return productPriceDtos;
    }

    public void setProductPriceDtos(Set<ProductPriceDto> productPriceDtos) {
        this.productPriceDtos = productPriceDtos;
    }
}
