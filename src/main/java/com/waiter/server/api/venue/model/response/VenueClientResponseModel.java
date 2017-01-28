package com.waiter.server.api.venue.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.menu.model.response.MenuWithProductsModel;

/**
 * Created by hovsep on 1/21/17.
 */
public class VenueClientResponseModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String name;

    private LocationModel location;

    private String image;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MenuWithProductsModel menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MenuWithProductsModel getMenu() {
        return menu;
    }

    public void setMenu(MenuWithProductsModel menu) {
        this.menu = menu;
    }
}