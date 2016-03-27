package com.waiter.server.services.product.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public class ProductDto extends AbstractDtoModel<Product> {

    private Set<Tag> tags;
    private Double price;

    @Override
    public void updateProperties(Product product) {
        if (getTags() != null) {
            product.setTags(getTags());
        }
        if (getPrice() != null) {
            product.setPrice(getPrice());
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
