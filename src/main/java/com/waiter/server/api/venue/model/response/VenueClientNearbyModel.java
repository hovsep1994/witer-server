package com.waiter.server.api.venue.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.api.location.model.LocationClientModel;
import com.waiter.server.api.product.model.response.ProductClientModel;
import com.waiter.server.api.search.model.response.VenueSearchModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * Created by hovsep on 1/21/17.
 */
public class VenueClientNearbyModel extends VenueSearchModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductClientModel> products;

    public List<ProductClientModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductClientModel> products) {
        this.products = products;
    }

    public static VenueClientNearbyModel convert(Venue venue, Language language, List<Product> topProducts) {
        final VenueClientNearbyModel venueModel = new VenueClientNearbyModel();
        venueModel.setId(venue.getId());
        venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery(), GalleryImageType.LOGO, false));
        if (venueModel.getImage() == null) {
            venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.VENUE, venue.getGallery()));
        }
        venueModel.setName(venue.getName());
        venueModel.setLocation(LocationClientModel.convert(venue.getLocation()));
        venueModel.setRating(venue.getEvaluation().getAverageRating());
        if (topProducts != null) {
            Language menuLanguage = venue.getMenu().getLanguages().contains(language) ? language : venue.getMenu().getMainLanguage();
            List<ProductClientModel> products = ProductClientModel.convert(topProducts, menuLanguage);
            venueModel.setProducts(products);
        }
        return venueModel;
    }

    public static VenueClientNearbyModel convert(Venue venue) {
        return convert(venue, null, null);
    }
}
