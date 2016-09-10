package com.waiter.server.services.product.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.product.model.ProductPrice;

/**
 * Created by hovsep on 9/10/16.
 */
public class ProductPriceDto{
    private Long id;
    private Double price;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


