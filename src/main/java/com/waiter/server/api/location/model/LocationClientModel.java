package com.waiter.server.api.location.model;

import com.waiter.server.services.location.model.Location;

/**
 * @author shahenpoghosyan
 */
public class LocationClientModel {

    private Double latitude;

    private Double longitude;

    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static LocationClientModel convert(Location location) {
        LocationClientModel locationClientModel = new LocationClientModel();
        locationClientModel.setLatitude(location.getLatitude());
        locationClientModel.setLongitude(location.getLongitude());
        locationClientModel.setAddress(location.getStreet() + ", " + location.getZip()
                + " " + location.getCity());
        return locationClientModel;
    }
}
