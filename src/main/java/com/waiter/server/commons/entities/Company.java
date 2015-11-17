package com.waiter.server.commons.entities;

import java.util.List;

/**
 * Created by Admin on 10/18/2015.
 */
public class Company {

    private int id;
    private String name;
    private String phone;
    private List<Menu> menus;
    private List<Venue> venues;

    public Company setId(int id) {
        this.id = id;
        return this;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public Company setMenus(List<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Company setVenues(List<Venue> venues) {
        this.venues = venues;
        return this;
    }

    public Company setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    
    public List<Menu> getMenus() {
        return menus;
    }

    public List<Venue> getVenues() {
        return venues;
    }




}
