package com.waiter.server.api.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.product.model.AbstractProductModel;

/**
 * Created by hovsep on 3/6/16.
 */
public class AddProductRequest extends AbstractProductModel{

    @JsonProperty(value = "groupId")
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
