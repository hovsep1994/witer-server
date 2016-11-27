package com.waiter.server.services.venue.dto;

import com.waiter.server.api.venue.model.request.VenueRequest;

/**
 * @author shahenpoghosyan
 */
public class VenueDto {

    private String name;
    private Long menuId;
    private Long locationId;
    private Long companyId;

    public String getName() {
        return name;
    }

    public VenueDto setName(String name) {
        this.name = name;
        return this;
    }

    public Long getMenuId() {
        return menuId;
    }

    public VenueDto setMenuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public Long getLocationId() {
        return locationId;
    }

    public VenueDto setLocationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public VenueDto setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }
}
