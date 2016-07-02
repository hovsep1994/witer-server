package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.language.Language;

/**
 * Created by hovsep on 6/1/16.
 */
public abstract class AbstractApiLanguageModel extends AbstractApiModel {

    @JsonProperty(value = "language")
    private Language language;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
