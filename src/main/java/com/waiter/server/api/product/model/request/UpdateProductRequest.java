package com.waiter.server.api.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.AbstractProductModel;

/**
 * Created by hovsep on 3/13/16.
 */
public class UpdateProductRequest extends AbstractProductModel {

    @JsonProperty(value = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
