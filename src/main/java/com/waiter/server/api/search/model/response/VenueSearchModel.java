package com.waiter.server.api.search.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.venue.model.Venue;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
public class VenueSearchModel {

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

    public String getImage() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }

    public static VenueSearchModel convert(Venue venue) {
        VenueSearchModel venueModel = new VenueSearchModel();
        venueModel.setId(venue.getId());
        venueModel.setName(venue.getName());
        venueModel.setCompanyId(venue.getCompany() == null ? null : venue.getCompany().getId());
        venueModel.setLocation(LocationModel.convert(venue.getLocation()));
        ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery(), GalleryImageType.MAIN);
        venueModel.setImageUrl(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery()));
        return venueModel;
    }

    public static List<VenueSearchModel> convert(Collection<Venue> venues) {
        return venues.stream().map(VenueSearchModel::convert).collect(Collectors.toList());
    }

    public static String getFullUrl(Set<GalleryImage> images, String cdnBaseUrl) {
        final GalleryImage galleryImage = images.stream()
                .filter(image -> image.getGalleryImageType() == GalleryImageType.MAIN)
                .findFirst().orElse(null);
        final String imageUrl = galleryImage != null ? galleryImage.getUrl() : "images/venue-image.png";
        return cdnBaseUrl + imageUrl;
    }
}
