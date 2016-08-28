package com.waiter.server.services.translation.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.translation.model.TranslationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by hovsep on 3/7/16.
 */
public class TranslationDto extends AbstractDtoModel<Translation> {

    private String text;
    private Language language;
    private TranslationType translationType = TranslationType.MAIN;

    public TranslationDto() {
    }

    public TranslationDto(String text, Language language) {
        this.text = text;
        this.language = language;
    }

    public TranslationDto(String text, Language language, TranslationType translationType) {
        this(text, language);
        this.translationType = translationType;
    }

    @Override
    public void updateProperties(Translation translation) {
        translation.setLanguage(getLanguage());
        translation.setText(getText());
        translation.setTranslationType(getTranslationType());
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TranslationDto that = (TranslationDto) o;

        return new EqualsBuilder()
                .append(text, that.text)
                .append(language, that.language)
                .append(translationType, that.translationType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(text)
                .append(language)
                .append(translationType)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .append("language", language)
                .append("translationType", translationType)
                .toString();
    }
}
