package com.waiter.server.api.category.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.name.model.requst.AddNameTranslationRequest;
import com.waiter.server.api.tag.model.TagModel;

import java.util.Set;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:30 PM
 */
public class AddCategoryModelRequest {

    @JsonProperty("menuId")
    private Long menuId;

    @JsonProperty("nameTranslation")
    private AddNameTranslationRequest addNameTranslationRequest;

    @JsonProperty(value = "tags", required = false)
    private Set<TagModel> tagModels;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public AddNameTranslationRequest getAddNameTranslationRequest() {
        return addNameTranslationRequest;
    }

    public void setAddNameTranslationRequest(AddNameTranslationRequest addNameTranslationRequest) {
        this.addNameTranslationRequest = addNameTranslationRequest;
    }

    public Set<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(Set<TagModel> tagModels) {
        this.tagModels = tagModels;
    }
}
