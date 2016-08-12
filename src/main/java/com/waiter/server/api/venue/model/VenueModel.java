package com.waiter.server.api.venue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.services.venue.model.Venue;

/**
 * Created by hovsep on 3/5/16.
 */
public class VenueModel {

    @JsonProperty
    private Long id;

    @JsonProperty
    private LocationModel location;

    @JsonProperty
    private Long companyId;

    @JsonProperty
    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public static VenueModel convert(Venue venue) {
        VenueModel venueModel = new VenueModel();
        venueModel.setId(venue.getId());
        venueModel.setMenuId(venue.getMenu() == null ? null : venue.getMenu().getId());
        venueModel.setCompanyId(venue.getCompany() == null ? null : venue.getCompany().getId());
        venueModel.setLocation(LocationModel.convert(venue.getLocation()));
        return venueModel;
    }
}
