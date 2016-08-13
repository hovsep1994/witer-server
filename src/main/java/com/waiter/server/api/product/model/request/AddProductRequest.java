package com.waiter.server.api.product.model.request;

/**
 * Created by hovsep on 8/13/16.
 */
public class AddProductRequest extends ProductRequest {

    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
