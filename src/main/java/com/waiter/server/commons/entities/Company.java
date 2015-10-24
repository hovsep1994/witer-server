package com.waiter.server.commons.entities;

import java.util.List;

/**
 * Created by Admin on 10/18/2015.
 */
public class Company {

    private int id;
    private String name;
    private String mail;
    private String phone;
    private Menu menu;
    private List<Venue> venues;

    public Company setId(int id) {
        this.id = id;
        return this;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public Company setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public Company setMenu(Menu menu) {
        this.menu = menu;
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

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<Venue> getVenues() {
        return venues;
    }
}
