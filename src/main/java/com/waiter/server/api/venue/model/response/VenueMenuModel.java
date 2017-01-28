package com.waiter.server.api.venue.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.venue.model.Venue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 8/28/16.
 */
public class VenueMenuModel {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Set<VenueMenuModel> convert(List<Venue> venues) {
        if (venues == null || venues.isEmpty()) {
            return new HashSet<>();
        }
        return venues.stream().map(venue -> {
            final VenueMenuModel model = new VenueMenuModel();
            model.setId(venue.getId());
            model.setName(venue.getName());
            final String url = ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery());
            model.setImage(url);
            return model;
        }).collect(Collectors.toSet());
    }
}
