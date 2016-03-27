package com.waiter.server.api.category.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.tag.model.TagModel;

import java.util.Set;

/**
 * Created by hovsep on 3/13/16.
 */
public abstract class AbstractCategoryModel {

    @JsonProperty(value = "nameTranslation", required = false)
    private NameTranslationModel nameTranslation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "tags", required = false)
    private Set<TagModel> tagModels;

    public NameTranslationModel getNameTranslation() {
        return nameTranslation;
    }

    public void setNameTranslation(NameTranslationModel nameTranslation) {
        this.nameTranslation = nameTranslation;
    }

    public Set<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(Set<TagModel> tagModels) {
        this.tagModels = tagModels;
    }
}
