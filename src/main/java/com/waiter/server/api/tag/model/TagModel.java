package com.waiter.server.api.tag.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.AbstractApiModel;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 5:31 PM
 */
public class TagModel extends AbstractApiModel {

    @JsonProperty(value = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
