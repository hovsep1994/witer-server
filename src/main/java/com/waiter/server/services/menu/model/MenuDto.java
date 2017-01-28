package com.waiter.server.services.menu.model;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;

/**
 * Created by hovsep on 9/10/16.
 */
public class MenuDto extends AbstractDtoModel<Menu> {

    private String name;
    private Language language;
    private Currency currency;

    @Override
    public void updateProperties(Menu menu) {
        if (getCurrency() != null)
            menu.setCurrency(getCurrency());
        if (getLanguage() != null) {
            menu.setMainLanguage(getLanguage());
            menu.getLanguages().add(getLanguage());
        }
        if (getName() != null) {
            menu.setName(getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
