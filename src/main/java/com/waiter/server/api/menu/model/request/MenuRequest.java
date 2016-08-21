package com.waiter.server.api.menu.model.request;

import com.waiter.server.services.currency.Currency;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:42 PM
 */
public class MenuRequest {

    private String name;

    private Currency currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
