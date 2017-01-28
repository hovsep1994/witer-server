package com.waiter.server.services.product.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.product.model.Product;

/**
 * Created by hovsep on 8/5/16.
 */
public class ProductUpdateEvent implements ApplicationEvent {

    private Product product;

    public ProductUpdateEvent(Product product) {
        this.product = product;
    }

    public Product getProductId() {
        return product;
    }
}
