package com.waiter.server.api.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.name.model.requst.AddNameTranslationRequest;

/**
 * Created by hovsep on 3/7/16.
 */
public class AddProductTranslationRequest {
    @JsonProperty(value = "id")
    private Long productId;

    @JsonProperty(value = "nameTranslation")
    private AddNameTranslationRequest addNameTranslationRequest;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public AddNameTranslationRequest getAddNameTranslationRequest() {
        return addNameTranslationRequest;
    }

    public void setAddNameTranslationRequest(AddNameTranslationRequest addNameTranslationRequest) {
        this.addNameTranslationRequest = addNameTranslationRequest;
    }
}
