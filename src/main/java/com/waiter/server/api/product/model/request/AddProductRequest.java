package com.waiter.server.api.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.requst.AddNameTranslationRequest;
import com.waiter.server.api.product.model.AbstractProductModel;

/**
 * Created by hovsep on 3/6/16.
 */
public class AddProductRequest extends AbstractProductModel{

    @JsonProperty(value = "groupId")
    private Long groupId;

    @JsonProperty(value = "nameTranslation")
    private AddNameTranslationRequest addNameTranslationRequest;

    public AddNameTranslationRequest getAddNameTranslationRequest() {
        return addNameTranslationRequest;
    }

    public void setAddNameTranslationRequest(AddNameTranslationRequest addNameTranslationRequest) {
        this.addNameTranslationRequest = addNameTranslationRequest;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
