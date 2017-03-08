package com.waiter.server.background.importer.model;

import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.venue.dto.VenueDto;

/**
 * @author shahenpoghosyan
 */
public class ParsedVenue extends VenueDto {

    private LocationDto locationDto;
    private String imageUrl;
    private String logo;

    public String getImageUrl() {
        return imageUrl;
    }

    public ParsedVenue setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocationDto getLocationDto() {
        return locationDto;
    }

    public ParsedVenue setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public ParsedVenue setLogo(String logo) {
        this.logo = logo;
        return this;
    }
}
