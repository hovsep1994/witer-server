package com.waiter.server.background.importer.parser.model;

import com.waiter.server.services.product.dto.ProductDto;
import com.waiter.server.services.product.model.Product;

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
