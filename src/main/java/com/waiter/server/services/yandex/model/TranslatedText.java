package com.waiter.server.services.yandex.model;

/**
 * Created by Admin on 1/17/2016.
 */
public class TranslatedText {

    private String text;
    private String langFrom;
    private String langTo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(String langFrom) {
        this.langFrom = langFrom;
    }

    public String getLangTo() {
        return langTo;
    }

    public void setLangTo(String langTo) {
        this.langTo = langTo;
    }
}
