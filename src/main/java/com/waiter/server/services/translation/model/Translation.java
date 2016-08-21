package com.waiter.server.services.translation.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.language.Language;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "translation")
public class Translation extends AbstractEntityModel {

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "translation_type", nullable = false)
    private TranslationType translationType;

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

    public TranslationType getTranslationType() {
        return translationType;
    }

    public void setTranslationType(TranslationType translationType) {
        this.translationType = translationType;
    }

    public static List<String> getListOfTexts(Collection<Translation> translations) {
        return translations.stream().map(Translation::getText).collect(Collectors.toList());
    }
}
