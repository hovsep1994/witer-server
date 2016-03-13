package com.waiter.server.services.translate.dto;

import com.waiter.server.services.language.Language;

import java.util.List;

/**
 * Created by hovsep on 3/7/16.
 */
public class TextsTranslationDto {

    private List<String> texts;
    private Language languageFrom;
    private Language languageTo;

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
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
