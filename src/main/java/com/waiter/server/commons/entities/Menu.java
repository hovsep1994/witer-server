package com.waiter.server.commons.entities;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Menu {
    private long id;
    private Venue venue;
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public Menu setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Venue getVenue() {
        return venue;
    }

    public Menu setVenue(Venue venue) {
        this.venue = venue;
        return this;
    }

    public long getId() {
        return id;
    }

    public Menu setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", venue=" + venue +
                ", groups=" + groups +
                '}';
    }
}
