package com.waiter.server.api.venue.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.AbstractApiModel;
import com.waiter.server.api.location.model.LocationModel;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:49 PM
 */
public class AddVenueRequest extends AbstractApiModel {

    @JsonProperty(value = "companyId")
    private Long companyId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "location")
    private LocationModel location;

    @JsonProperty(value = "menuId", required = false)
    private Long menuId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
