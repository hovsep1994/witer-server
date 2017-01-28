package com.waiter.server.api.user.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shahenpoghosyan
 */
public class UserValidationModelRequest {

    @JsonProperty(value = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
