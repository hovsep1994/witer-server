package com.waiter.server.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author shahenpoghosyan
 */
public class ResponseEntity<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T reponse;
    private String status;

    public ResponseEntity() {

    }

    public ResponseEntity(T reponse) {
        this.reponse = reponse;
        this.status = "success";
    }

    public T getReponse() {
        return reponse;
    }

    public void setReponse(T reponse) {
        this.reponse = reponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
