package com.waiter.server.commons.entities;

public class Venue {
    private int id;
    private Company company;
    private Location location;
    private Menu menu;

    public Venue setId(int id) {
        this.id = id;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public Venue setCompany(Company company) {
        this.company = company;
        return this;
    }

    public Venue setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Venue setMenu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", company=" + company +
                ", location=" + location +
                ", menu=" + menu +
                '}';
    }
}
