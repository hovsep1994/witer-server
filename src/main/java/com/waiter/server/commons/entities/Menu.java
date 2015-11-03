package com.waiter.server.commons.entities;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Menu {
    private long id;
    private String name;
    private Company company;
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public Menu setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public Menu setCompany(Company company) {
        this.company = company;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Menu setId(long id) {
        this.id = id;
        return this;
    }

}
