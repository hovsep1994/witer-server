package com.waiter.server.api.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.model.AbstractApiLanguageModel;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.language.Language;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public abstract class AbstractProductModel extends AbstractApiLanguageModel {

    @JsonProperty(value = "tags", required = false)
    private Set<TagModel> tagModels;

    @JsonProperty(value = "price", required = false)
    private Double price;

    @JsonProperty(value = "description", required = false)
    private String description;

    @JsonProperty(value = "name")
    private String name;

    public Set<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(Set<TagModel> tagModels) {
        this.tagModels = tagModels;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
