package com.waiter.server.services.name.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.model.NameTranslation;
import com.waiter.server.services.name.model.TranslationType;

/**
 * Created by hovsep on 3/7/16.
 */
public class NameTranslationDto extends AbstractDtoModel<NameTranslation> {

    private String name;
    private Language language;
    private TranslationType translationType;

    @Override
    public void convertToEntityModel(NameTranslation nameTranslation) {
        nameTranslation.setLanguage(getLanguage());
        nameTranslation.setName(getName());
        nameTranslation.setTranslationType(getTranslationType());
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
