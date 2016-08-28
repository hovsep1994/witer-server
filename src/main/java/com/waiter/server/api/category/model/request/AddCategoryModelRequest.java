package com.waiter.server.api.category.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.TranslationModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;

import java.util.Set;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:30 PM
 */
public class AddCategoryModelRequest {

    @JsonProperty(value = "menuId", required = true)
    private Long menuId;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "language", required = true)
    private Language language;

    @JsonProperty(value = "tags")
    private Set<TagModel> tagModels;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public Set<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(Set<TagModel> tagModels) {
        this.tagModels = tagModels;
    }
}
