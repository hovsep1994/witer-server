package com.waiter.server.api.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.Location;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:50 PM
 */
public class LocationModel {

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "countryCode")
    private String countryCode;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "street")
    private String street;

    @JsonProperty(value = "zip", required = false)
    private String zip;

    @JsonProperty(value = "latitude")
    private Double latitude;

    @JsonProperty(value = "longitude")
    private Double longitude;

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

    public static LocationModel convert(Location location) {
        LocationModel locationModel = new LocationModel();
        locationModel.setCity(location.getCity());
        locationModel.setCountry(location.getCountry());
        locationModel.setCountryCode(location.getCountryCode());
        locationModel.setLatitude(location.getLatitude());
        locationModel.setLongitude(location.getLongitude());
        locationModel.setStreet(location.getStreet());
        locationModel.setZip(location.getZip());
        return locationModel;
    }

    public static Location convert(LocationModel locationModel) {
        Location location = new Location();
        location.setCity(locationModel.getCity());
        location.setCountry(locationModel.getCountry());
        location.setCountryCode(locationModel.getCountryCode());
        location.setLatitude(locationModel.getLatitude());
        location.setLongitude(locationModel.getLongitude());
        location.setStreet(locationModel.getStreet());
        location.setZip(locationModel.getZip());
        return location;
    }

    public static LocationDto convertToDto(LocationModel locationModel) {
        LocationDto dto = new LocationDto();
        dto.setCity(locationModel.getCity());
        dto.setCountry(locationModel.getCountry());
        dto.setCountryCode(locationModel.getCountryCode());
        dto.setLatitude(locationModel.getLatitude());
        dto.setLongitude(locationModel.getLongitude());
        dto.setStreet(locationModel.getStreet());
        dto.setZip(locationModel.getZip());
        return dto;
    }
}
