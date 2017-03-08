package com.waiter.server.api.search.model.request;

/**
 * @author shahenpoghosyan
 */
public class SearchParams {

    private String query;
    private double latitude;
    private double longitude;
    private int offset;
    private int limit;


    public String getQuery() {
        return query;
    }

    public SearchParams setQuery(String query) {
        this.query = query;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public SearchParams setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public SearchParams setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public SearchParams setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public SearchParams setLimit(int limit) {
        this.limit = limit;
        return this;
    }
}
