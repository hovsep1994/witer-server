package com.waiter.server.api.user.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 4:58 PM
 */
public class UserRegistrationModelRequest {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty()
    private String email;

    @JsonProperty()
    private String password;

    @JsonProperty(required = false)
    private String phone;

    @JsonProperty()
    private String companyName;

    @JsonProperty(required = false)
    private String language;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
