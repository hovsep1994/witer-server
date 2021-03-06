package com.waiter.server.api.menu.model.response;

import com.waiter.server.api.category.model.response.CategoryMenuModel;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;

import java.util.List;
import java.util.Set;

/**
 * Created by hovsep on 8/18/16.
 */
public class MenuWithProductsModel {

    private Long id;
    private String name;
    private Language mainLanguage;
    private Currency currency;
    private List<CategoryMenuModel> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(Language language) {
        this.mainLanguage = language;
    }

    public List<CategoryMenuModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryMenuModel> categories) {
        this.categories = categories;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    private static MenuWithProductsModel convert(Menu menu, Language language, boolean useProductImages) {
        MenuWithProductsModel menuModel = new MenuWithProductsModel();
        menuModel.setId(menu.getId());
        menuModel.setName(menu.getName());
        menuModel.setMainLanguage(language);
        menuModel.setCurrency(menu.getCurrency());
        menuModel.setCategories(CategoryMenuModel.convert(menu.getCategories(), language, useProductImages));
        return menuModel;
    }

    public static MenuWithProductsModel convertForClient(Menu menu, Language language) {
        return convert(menu, language, true);
    }

    public static MenuWithProductsModel convertForCustomer(Menu menu, Language language) {
        return convert(menu, language, false);
    }
}
