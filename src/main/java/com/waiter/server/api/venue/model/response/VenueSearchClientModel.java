package com.waiter.server.api.venue.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.product.model.response.ProductMenuModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * Created by hovsep on 1/21/17.
 */
public class VenueSearchClientModel {

    private Long id;

    private String name;

    private LocationModel location;

    private String image;

    private double rating;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductMenuModel> products;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductMenuModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductMenuModel> products) {
        this.products = products;
    }

    public double getRating() {
        return rating;
    }

    public VenueSearchClientModel setRating(double rating) {
        this.rating = rating;
        return this;
    }


    public static VenueSearchClientModel convert(Venue venue, Language language, List<Product> topProducts) {
        final VenueSearchClientModel venueModel = new VenueSearchClientModel();
        venue.setId(venue.getId());
        venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery()));
        venueModel.setName(venue.getName());
        venueModel.setLocation(LocationModel.convert(venue.getLocation()));
        venueModel.setRating(venue.getEvaluation().getAverageRating());
        Language menuLanguage = venue.getMenu().getLanguages().contains(language) ? language : venue.getMenu().getMainLanguage();
        List<ProductMenuModel> products = ProductMenuModel.convert(topProducts, menuLanguage);
        venueModel.setProducts(products);
        return venueModel;
    }
}
