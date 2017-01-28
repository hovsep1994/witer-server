package com.waiter.server.api.translate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.language.Language;

/**
 * Created by hovsep on 3/7/16.
 */
public class TranslateTextModel {

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "languageFrom")
    private Language languageFrom;

    @JsonProperty(value = "languageTo")
    private Language languageTo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(Language languageFrom) {
        this.languageFrom = languageFrom;
    }

    public Language getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(Language languageTo) {
        this.languageTo = languageTo;
    }
}
