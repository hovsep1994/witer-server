package com.waiter.server.api.venue.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 1/19/2016.
 */
public class VenueSearchParametersRequest {

    @JsonProperty(value = "name", required = false)
    private String name;

    @JsonProperty(value = "name")
    private double latitude;

    @JsonProperty(value = "name")
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VenueSearchParametersRequest that = (VenueSearchParametersRequest) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "VenueSearchParametersRequest{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
