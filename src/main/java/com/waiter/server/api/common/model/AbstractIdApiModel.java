package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hovsep on 3/6/16.
 */
public abstract class AbstractIdApiModel {

    @JsonProperty(value = "id", required = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
