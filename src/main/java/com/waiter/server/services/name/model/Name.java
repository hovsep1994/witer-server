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

}
