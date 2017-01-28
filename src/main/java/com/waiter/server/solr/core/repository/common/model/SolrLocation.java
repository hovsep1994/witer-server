package com.waiter.server.solr.core.repository.common.model;

/**
 * @author shahenpoghosyan
 */
public class SolrLocation {

    private double latitude;
    private double longitude;

    public SolrLocation() {
    }

    public SolrLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
