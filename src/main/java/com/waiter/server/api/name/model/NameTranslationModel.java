package com.waiter.server.api.name.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.model.NameTranslation;

/**
 * Created by hovsep on 3/5/16.
 */
public class NameTranslationModel extends AbstractNameTranslationModel{

    @JsonProperty(value = "id", required = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static NameTranslationModel convert(NameTranslation nameTranslation) {
        NameTranslationModel nameTranslationModel = new NameTranslationModel();
        nameTranslationModel.setId(nameTranslation.getId());
        nameTranslationModel.setName(nameTranslation.getName());
        nameTranslationModel.setLanguage(nameTranslation.getLanguage());
        return nameTranslationModel;
    }
}
