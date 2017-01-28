package com.waiter.server.api.category.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.category.model.AbstractCategoryModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.language.Language;

import java.util.Set;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:30 PM
 */
public class AddCategoryModelRequest extends AbstractCategoryModel {

    @JsonProperty(value = "menuId", required = true)
    private Long menuId;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }


}
