package com.waiter.server.services.evaluation.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Column;

/**
 * Created by hovsep on 3/12/16.
 */
public class Rate extends AbstractEntityModel{

    @Column(name = "customerToken", nullable = false)
    private String customerToken;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
