package com.waiter.server.api.menu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 3/5/16.
 */
public class MenuModel {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    private Language language;

    private Set<Long> attachedVenues;

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

    public Set<Long> getAttachedVenues() {
        return attachedVenues;
    }

    public void setAttachedVenues(Set<Long> attachedVenues) {
        this.attachedVenues = attachedVenues;
    }

    public static MenuModel convert(Menu menu) {
        final MenuModel menuModel = new MenuModel();
        menuModel.setId(menu.getId());
        menuModel.setName(menu.getName());
        menuModel.setLanguage(menu.getMainLanguage());
        menuModel.setAttachedVenues(menu.getVenues().stream()
                .map(AbstractEntityModel::getId)
                .collect(Collectors.toSet()));
        return menuModel;
    }
}
