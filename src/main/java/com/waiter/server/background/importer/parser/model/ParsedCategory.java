package com.waiter.server.background.importer.parser.model;


import com.waiter.server.services.category.dto.CategoryDto;

/**
 * @author shahenpoghosyan
 */
public class ParsedCategory extends CategoryDto {
    private String productRef;

    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }
}
