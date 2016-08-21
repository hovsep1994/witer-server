package com.waiter.server.api.menu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;

import java.util.List;

/**
 * Created by hovsep on 3/5/16.
 */
public class MenuModel {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    private Language language;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public static MenuModel convert(Menu menu) {
        MenuModel menuModel = new MenuModel();
        menuModel.setId(menu.getId());
        menuModel.setName(menu.getName());
        menuModel.setLanguage(menu.getMainLanguage());
        return menuModel;
    }
}
