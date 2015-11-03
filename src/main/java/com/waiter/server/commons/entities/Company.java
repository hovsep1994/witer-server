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
    private String password;
    private String token;
    private String hash;
    private List<Menu> menus;
    private List<Venue> venues;
    
    public String getPassword() {
        return password;
    }

    public Company setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Company setToken(String token) {
        this.token = token;
        return this;
    }

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

    public String getMail() {
        return mail;
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

    public String getHash() {
        return hash;
    }

    public Company setHash(String hash) {
        this.hash = hash;
        return this;
    }


}
