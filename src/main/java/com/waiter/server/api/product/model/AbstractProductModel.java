package com.waiter.server.api.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.tag.model.TagModel;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public abstract class AbstractProductModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "tags", required = false)
    private Set<TagModel> tagModels;

    @JsonProperty(value = "price", required = false)
    private Double price;

    @JsonProperty(value = "description", required = false)
    private String description;

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
}
