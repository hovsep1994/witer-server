package com.waiter.server.services.category.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.tag.model.Tag;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public class CategoryDto extends AbstractDtoModel<Category> {

    private Set<Tag> tags;

    @Override
    public void updateProperties(Category category) {
        if (getTags() != null) {
            category.setTags(getTags());
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
