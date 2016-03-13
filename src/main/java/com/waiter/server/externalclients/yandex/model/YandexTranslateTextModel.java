package com.waiter.server.externalclients.yandex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Admin on 2/2/2016.
 */
public class YandexTranslateTextModel {

    @JsonProperty("code")
    private String code;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("text")
    private List<String> texts;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
