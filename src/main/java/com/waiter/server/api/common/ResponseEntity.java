package com.waiter.server.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author shahenpoghosyan
 */
public class ResponseEntity<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;
    private String status;

    public ResponseEntity() {
    }

    public ResponseEntity(T response) {
        this.response = response;
        this.status = "success";
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
