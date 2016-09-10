package com.waiter.server.api.menu.model.request;

import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.MenuDto;

import java.util.Set;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:42 PM
 */
public class MenuRequest {

    private String name;

    private Currency currency;

    private Language language;

    private Set<Long> venues;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Long> getVenues() {
        return venues;
    }

    public void setVenues(Set<Long> venues) {
        this.venues = venues;
    }

    public static MenuDto convert(MenuRequest request) {
        final MenuDto menuDto = new MenuDto();
        menuDto.setLanguage(request.getLanguage());
        menuDto.setName(request.getName());
        menuDto.setCurrency(request.getCurrency());
        return menuDto;
    }
}
