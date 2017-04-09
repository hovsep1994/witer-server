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

    @Column(name = "text", nullable = false, length = 65000)
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

    public static Translation getTranslationByLanguage(Collection<Translation> translations, Language language) {
        return translations.stream().filter(translation ->
                translation.getLanguage().equals(language)).findFirst().orElse(null);
    }

    public static Translation getTranslationByLanguages(Collection<Translation> translations, List<Language> languages) {
        for (Language language : languages) {
            Translation translation = getTranslationByLanguage(translations, language);
            if (translation != null) {
                return translation;
            }
        }
        return translations.stream().findAny().get();
    }

    public static String getTranslationTextByLanguage(Collection<Translation> translations, Language language) {
        final Translation translation = translations.stream().filter(t ->
                t.getLanguage().equals(language)).findFirst().orElse(null);
        return translation == null ? null : translation.getText();

    }
}
