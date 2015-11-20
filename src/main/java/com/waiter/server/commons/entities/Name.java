package com.waiter.server.commons.entities;

import com.waiter.server.commons.EntityType;
import com.waiter.server.commons.TranslationType;

/**
 * @author shahenpoghosyan
 */
public class Name {

    private int id;
    private String name;
    private Language language;
    private int entityId;
    private EntityType entityType;
    private TranslationType translationType;

    public TranslationType getTranslationType() {
        return translationType;
    }

    public Name setTranslationType(TranslationType translationType) {
        this.translationType = translationType;
        return this;
    }

    public int getId() {
        return id;
    }

    public Name setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Name setName(String name) {
        this.name = name;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public Name setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Name setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Name setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }
}
