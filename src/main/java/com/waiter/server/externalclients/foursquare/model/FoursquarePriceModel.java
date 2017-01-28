package com.waiter.server.externalclients.foursquare.model;

/**
 * Created by hovsep on 5/15/16.
 */
public class FoursquarePriceModel {

    private int tier;
    private String message;
    private String currency;

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
