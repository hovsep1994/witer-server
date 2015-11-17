package com.waiter.server.commons.entities;

/**
 * @author shahenpoghosyan
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String token;
    private String hash;
    private Company company;

    public String getHash() {
        return hash;
    }

    public User setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public User setCompany(Company company) {
        this.company = company;
        return this;
    }
}
