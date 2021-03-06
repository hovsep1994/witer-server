package com.waiter.server.api.venue.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.model.AbstractApiModel;
import com.waiter.server.api.location.model.LocationModel;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:49 PM
 */
public class VenueRequest extends AbstractApiModel {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "location")
    private LocationModel location;

    @JsonProperty(value = "menuId")
    private Long menuId;

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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}
