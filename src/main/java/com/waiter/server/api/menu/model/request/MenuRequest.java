package com.waiter.server.api.menu.model.request;

import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:42 PM
 */
public class MenuRequest {

    private String name;

    private Currency currency;

    private Language mainLanguage;

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

    public Language getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(Language mainLanguage) {
        this.mainLanguage = mainLanguage;
    }
}
