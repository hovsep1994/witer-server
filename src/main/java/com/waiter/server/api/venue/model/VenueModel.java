package com.waiter.server.api.venue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.venue.model.Venue;

import java.util.Set;

/**
 * Created by hovsep on 3/5/16.
 */
public class VenueModel {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private LocationModel location;

    @JsonProperty
    private Long companyId;

    @JsonProperty
    private String image;

    @JsonProperty
    private Long menuId;

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

    public String getImage() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }

    public static VenueModel convert(Venue venue, String cdnBaseUrl) {
        VenueModel venueModel = new VenueModel();
        venueModel.setId(venue.getId());
        venueModel.setName(venue.getName());
        venueModel.setMenuId(venue.getMenu() == null ? null : venue.getMenu().getId());
        venueModel.setCompanyId(venue.getCompany() == null ? null : venue.getCompany().getId());
        venueModel.setLocation(LocationModel.convert(venue.getLocation()));
        ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery(), GalleryImageType.MAIN);
        venueModel.setImageUrl(getFullUrl(venue.getGallery().getGalleryImages(), cdnBaseUrl));
        return venueModel;
    }

    public static String getFullUrl(Set<GalleryImage> images, String cdnBaseUrl) {
        final GalleryImage galleryImage = images.stream()
                .filter(image -> image.getGalleryImageType() == GalleryImageType.MAIN)
                .findFirst().orElse(null);
        final String imageUrl = galleryImage != null ? galleryImage.getUrl() : "images/venue-image.png";
        return cdnBaseUrl + imageUrl;
    }
}
