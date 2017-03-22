package com.waiter.server.api.search.model.response;

import com.waiter.server.api.location.model.LocationClientModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.venue.model.Venue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
public class VenueSearchModel {

    private Long id;

    private String name;

    private LocationClientModel location;

    private String image;

    private double rating;

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

    public LocationClientModel getLocation() {
        return location;
    }

    public void setLocation(LocationClientModel location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public static VenueSearchModel convert(Venue venue) {
        VenueSearchModel venueModel = new VenueSearchModel();
        venueModel.setId(venue.getId());
        venueModel.setName(venue.getName());
        venueModel.setLocation(LocationClientModel.convert(venue.getLocation()));
        venueModel.setRating(venue.getEvaluation().getAverageRating());
        venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery()));
        return venueModel;
    }

    public static List<VenueSearchModel> convert(Collection<Venue> venues) {
        return venues.stream().map(VenueSearchModel::convert).collect(Collectors.toList());
    }
}
