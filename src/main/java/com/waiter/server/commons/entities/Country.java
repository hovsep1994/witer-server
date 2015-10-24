package com.waiter.server.commons.entities;

import java.util.Arrays;

/**
 * @author shahenpoghosyan
 */
public class Country {

    private String name;
    private String code;
    private String currency;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Country addName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Country addCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", currency=" + currency +
                '}';
    }
}
