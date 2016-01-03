package com.waiter.server.services.name.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import com.waiter.server.services.common.model.AbstractNamedEntityModel;
import com.waiter.server.services.language.model.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "name")
public class Name extends AbstractNamedEntityModel {

    private Language language;
    private int entityId;
    private EntityType entityType;
    private TranslationType translationType;

    public Language getLanguage() {

        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public TranslationType getTranslationType() {
        return translationType;
    }

    public void setTranslationType(TranslationType translationType) {
        this.translationType = translationType;
    }
}
