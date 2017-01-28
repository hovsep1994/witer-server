package com.waiter.server.api.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.model.AbstractApiLanguageModel;
import com.waiter.server.api.common.model.AbstractApiModel;
import com.waiter.server.api.product.model.ProductPriceModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.dto.ProductPriceDto;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 3/6/16.
 */
public class ProductRequest extends AbstractApiModel {

    @JsonProperty(value = "tags", required = false)
    private Set<String> tags;

    @JsonProperty(value = "productPrices")
    private Set<ProductPriceModel> productPrices;

    @JsonProperty(value = "description", required = false)
    private String description;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "language", required = true)
    private Language language;

    private Boolean isAvailable;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public static ProductDto convertToProductDto(ProductRequest request) {
        final ProductDto productDto = new ProductDto();
        productDto.setTags(request.getTags());
        productDto.setAvailable(request.getAvailable());
        productDto.setName(request.getName());
        productDto.setDescription(request.getDescription());
        productDto.setLanguage(request.getLanguage());
        productDto.setProductPriceDtos(convertToProductPriceDto(request));
        return productDto;
    }

    public static Set<ProductPriceDto> convertToProductPriceDto(ProductRequest request) {
        if (request.getProductPrices() == null) {
            return null;
        }
        return request.getProductPrices().stream().map(productPriceModel -> {
            ProductPriceDto productPriceDto = new ProductPriceDto();
            productPriceDto.setId(productPriceModel.getId());
            productPriceDto.setPrice(productPriceModel.getPrice());
            productPriceDto.setName(productPriceModel.getName());
            return productPriceDto;
        }).collect(Collectors.toSet());
    }
}
