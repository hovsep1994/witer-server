package com.waiter.server.api.name.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.language.Language;

/**
 * Created by hovsep on 3/6/16.
 */
public abstract class AbstractTranslationModel {

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "language")
    private Language language;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
