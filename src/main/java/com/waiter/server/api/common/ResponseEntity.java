package com.waiter.server.api.common;

/**
 * @author shahenpoghosyan
 */
public class ResponseEntity<T> {

    private T reponse;
    private String status;

    public ResponseEntity(T reponse) {
        this.reponse = reponse;
        this.status = "success";
    }

}
