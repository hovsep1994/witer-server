package com.waiter.server.api.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 5:50 PM
 */
public class LocationModel {

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "street")
    private String street;

    @JsonProperty(value = "zip", required = false)
    private String zip;

    @JsonProperty(value = "latitude")
    private double latitude;

    @JsonProperty(value = "longitude")
    private double longitude;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
