package com.waiter.server.commons.entities;

import java.util.Arrays;

/**
 * Created by Admin on 10/20/2015.
 */
public class Location {

    private String country;
    private String city;
    private String street;
    private String zip;
    private double[] latLng = new double[2];

    public Location setCountry(String country) {
        this.country = country;
        return this;
    }

    public Location setCity(String city) {
        this.city = city;
        return this;
    }

    public Location setStreet(String street) {
        this.street = street;
        return this;
    }

    public Location setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public Location setLatLng(double[] latLng) {
        this.latLng = latLng;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public double[] getLatLng() {
        return latLng;
    }
}
