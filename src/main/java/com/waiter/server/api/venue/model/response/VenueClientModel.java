package com.waiter.server.api.venue.model.response;

import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.menu.model.response.MenuWithProductsModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.venue.model.Venue;

/**
 * @author shahenpoghosyan
 */
public class VenueClientModel {

    private Long id;

    private String name;

    private LocationModel location;

    private String image;

    private double rating;

    //todo create separate client menu model and change this
    private MenuWithProductsModel menu;


    public Long getId() {
        return id;
    }

    public VenueClientModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public VenueClientModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocationModel getLocation() {
        return location;
    }

    public VenueClientModel setLocation(LocationModel location) {
        this.location = location;
        return this;
    }

    public String getImage() {
        return image;
    }

    public VenueClientModel setImage(String image) {
        this.image = image;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public VenueClientModel setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public MenuWithProductsModel getMenu() {
        return menu;
    }

    public VenueClientModel setMenu(MenuWithProductsModel menu) {
        this.menu = menu;
        return this;
    }

    public static VenueClientModel convert(Venue venue, Language language) {
        VenueClientModel venueModel = new VenueClientModel();
        venueModel.setId(venue.getId());
        venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery()));
        venueModel.setName(venue.getName());
        venueModel.setLocation(LocationModel.convert(venue.getLocation()));
        venueModel.setRating(venue.getEvaluation().getAverageRating());
        venueModel.setMenu(MenuWithProductsModel.convert(venue.getMenu(), language));
        return venueModel;
    }
}
