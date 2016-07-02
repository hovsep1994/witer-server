package com.waiter.server.api.rate.model;

import com.waiter.server.api.common.model.AbstractApiLanguageModel;

/**
 * Created by hovsep on 6/1/16.
 */
public class RateRequest extends AbstractApiLanguageModel{

    private Long id;
    private String token;
    private Integer rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
