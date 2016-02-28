package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:26 PM
 */
public class ResponseStatus {

    @JsonProperty(value = "status")
    String status;

    public ResponseStatus() {
    }

    public ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
