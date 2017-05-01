package com.waiter.server.api.venue.model.response;

import com.waiter.server.api.location.model.LocationClientModel;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.menu.model.response.MenuWithProductsModel;
import com.waiter.server.api.product.model.response.ProductClientModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class VenueClientModel {

    private Long id;

    private String name;

    private LocationClientModel location;

    private String image;

    private String logo;

    private double rating;

    //todo create separate client menu model and change this
    private MenuWithProductsModel menu;

    private List<ScheduleClientModel> openHours;


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

    public LocationClientModel getLocation() {
        return location;
    }

    public VenueClientModel setLocation(LocationClientModel location) {
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

    public List<ScheduleClientModel> getOpenHours() {
        return openHours;
    }

    public void setOpenHours(List<ScheduleClientModel> openHours) {
        this.openHours = openHours;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public static VenueClientModel convert(Venue venue, Language language) {
        VenueClientModel venueModel = new VenueClientModel();
        venueModel.setId(venue.getId());
        venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery()));
        venueModel.setLogo(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery(), GalleryImageType.LOGO));
        venueModel.setName(venue.getName());
        venueModel.setLocation(LocationClientModel.convert(venue.getLocation()));
        venueModel.setRating(venue.getEvaluation().getAverageRating());
        venueModel.setMenu(MenuWithProductsModel.convertForClient(venue.getMenu(), language));
        venueModel.setOpenHours(ScheduleClientModel.convert(venue.getOpenHours()));
        return venueModel;
    }
}
