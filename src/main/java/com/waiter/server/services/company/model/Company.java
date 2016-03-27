package com.waiter.server.services.company.model;


import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.venue.model.Venue;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "company")
public class Company extends AbstractEntityModel {

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Menu> menus;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Venue> venues;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

