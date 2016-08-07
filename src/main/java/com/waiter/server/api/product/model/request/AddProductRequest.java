package com.waiter.server.api.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.AbstractProductModel;

/**
 * Created by hovsep on 3/6/16.
 */
public class AddProductRequest extends AbstractProductModel{

    @JsonProperty(value = "categoryId")
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
