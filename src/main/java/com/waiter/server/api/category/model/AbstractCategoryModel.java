package com.waiter.server.api.category.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;

import java.util.Set;

/**
 * Created by hovsep on 3/13/16.
 */
public abstract class AbstractCategoryModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "tags", required = false)
    private Set<String> tags;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "language", required = true)
    private Language language;

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
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
}
