package com.waiter.server.services.venue.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.model.Menu;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "venue")
public class Venue extends AbstractEntityModel{

    private Company company;
    private Location location;
    private Menu menu;

}
