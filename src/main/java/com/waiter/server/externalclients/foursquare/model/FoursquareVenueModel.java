package com.waiter.server.externalclients.foursquare.model;

import java.util.List;

/**
 * Created by hovsep on 5/15/16.
 */
public class FoursquareVenueModel {

    private String id;
    private String name;
    private Double rating;
    private Boolean verified;
    private FoursquareLocationModel location;
    private FoursquarePriceModel price;
    private List<FoursquareCategoryModel> categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public FoursquareLocationModel getLocation() {
        return location;
    }

    public void setLocation(FoursquareLocationModel location) {
        this.location = location;
    }

    public FoursquarePriceModel getPrice() {
        return price;
    }

    public void setPrice(FoursquarePriceModel price) {
        this.price = price;
    }

    public List<FoursquareCategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<FoursquareCategoryModel> categories) {
        this.categories = categories;
    }
}
