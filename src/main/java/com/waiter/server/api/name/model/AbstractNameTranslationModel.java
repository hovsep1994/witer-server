package com.waiter.server.api.name.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.language.Language;

/**
 * Created by hovsep on 3/6/16.
 */
public abstract class AbstractNameTranslationModel {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "language")
    private Language language;

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
