package com.waiter.server.services.translation.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.translation.model.TranslationType;

/**
 * Created by hovsep on 3/7/16.
 */
public class TranslationDto extends AbstractDtoModel<Translation> {

    private String name;
    private Language language;
    private TranslationType translationType;

    public TranslationDto() {
        translationType = TranslationType.MAIN;
    }

    public TranslationDto(String name, Language language) {
        this();
        this.name = name;
        this.language = language;
    }

    @Override
    public void updateProperties(Translation translation) {
        translation.setLanguage(getLanguage());
        translation.setName(getName());
        translation.setTranslationType(getTranslationType());
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

    public TranslationType getTranslationType() {
        return translationType;
    }

    public void setTranslationType(TranslationType translationType) {
        this.translationType = translationType;
    }

}
