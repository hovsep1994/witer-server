package com.waiter.server.api.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 4:57 PM
 */
public class UserModel {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value ="token")
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
