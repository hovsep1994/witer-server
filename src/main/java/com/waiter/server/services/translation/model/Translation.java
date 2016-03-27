package com.waiter.server.services.translation.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.language.Language;


import javax.persistence.*;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "translation")
public class Translation extends AbstractEntityModel {

    @Column(name = "text", nullable = false)
    private String name;

    @Column(name = "language", nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "translation_type", nullable = false)
    private TranslationType translationType;

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
