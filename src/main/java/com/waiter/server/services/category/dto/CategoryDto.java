package com.waiter.server.services.category.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.model.EntityType;
import com.waiter.server.services.name.model.NameTranslation;
import com.waiter.server.services.name.model.TranslationType;
import com.waiter.server.services.tag.model.Tag;

import java.util.Set;

/**
 * Created by hovsep on 3/6/16.
 */
public class CategoryDto extends AbstractDtoModel<Category> {

    private String groupName;
    private Language language;
    private Set<Tag> tags;

    @Override
    public void convertToEntityModel(Category category) {
        NameTranslation nameTranslation = new NameTranslation();
        nameTranslation.setLanguage(language);
        nameTranslation.setEntityType(EntityType.GROUP);
        nameTranslation.setName(groupName);
        nameTranslation.setTranslationType(TranslationType.MAIN);
        category.getNameTranslations().add(nameTranslation);
        if (tags != null) {
            category.getTags().addAll(tags);
        }
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
