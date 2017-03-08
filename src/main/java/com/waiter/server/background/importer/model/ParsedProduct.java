package com.waiter.server.background.importer.model;

import com.waiter.server.services.product.dto.ProductDto;

/**
 * @author shahenpoghosyan
 */
public class ParsedProduct extends ProductDto {

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public ParsedProduct setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
