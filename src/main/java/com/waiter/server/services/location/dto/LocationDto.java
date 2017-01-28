package com.waiter.server.services.location.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.location.model.Location;

/**
 * Created by hovsep on 8/28/16.
 */
public class LocationDto extends AbstractDtoModel<Location> {

    private String country;
    private String countryCode;
    private String city;
    private String street;
    private String zip;
    private Double latitude;
    private Double longitude;

    @Override
    public void updateProperties(Location location) {
        if (getCountry() != null) {
            location.setCountry(getCountry());
        }
        if (getCountryCode() != null) {
            location.setCountryCode(getCountryCode());
        }
        if (getCity() != null) {
            location.setCity(getCity());
        }
        if (getStreet() != null) {
            location.setStreet(getStreet());
        }
        if (getZip() != null) {
            location.setZip(getZip());
        }
        if (getLatitude() != null) {
            location.setLatitude(getLatitude());
        }
        if (getLongitude() != null) {
            location.setLongitude(getLongitude());
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
