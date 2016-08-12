package com.waiter.server.services.product.event;

import com.waiter.server.services.event.ApplicationEvent;

/**
 * Created by hovsep on 8/5/16.
 */
public class ProductUpdateEvent implements ApplicationEvent {

    private Long productId;

    public ProductUpdateEvent(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
