package com.waiter.server.services.venue.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by hovsep on 9/11/16.
 */
@Entity
@Table(name = "venue_type")
public class VenueType extends AbstractEntityModel {

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
