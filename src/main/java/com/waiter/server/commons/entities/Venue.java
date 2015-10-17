package com.waiter.server.commons.entities;

/**
 * Created by shahenpoghosyan on 6/25/15.
 */
public class Venue {

    private int id;
    private String code;
    private String name;
    private boolean validated;
    private String ownerEmail;

    public int getId() {
        return id;
    }

    public Venue setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Venue setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Venue setCode(String code) {
        this.code = code;
        return this;
    }

    public boolean isValidated() {
        return validated;
    }

    public Venue setValidated(boolean validated) {
        this.validated = validated;
        return this;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public Venue setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }
}
