package com.waiter.server.services.name.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import com.waiter.server.services.common.model.AbstractNamedEntityModel;
import com.waiter.server.services.language.model.Language;

import javax.persistence.*;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "name")
public class Name extends AbstractNamedEntityModel {

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "entity_id", nullable = false)
    private int entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "translation_type", nullable = false)
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
