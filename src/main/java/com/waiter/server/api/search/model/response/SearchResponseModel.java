package com.waiter.server.api.search.model.response;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class SearchResponseModel {

    List<VenueSearchModel> venues;
    List<ProductSearchModel> products;

    public List<VenueSearchModel> getVenues() {
        return venues;
    }

    public SearchResponseModel setVenues(List<VenueSearchModel> venues) {
        this.venues = venues;
        return this;
    }

    public List<ProductSearchModel> getProducts() {
        return products;
    }

    public SearchResponseModel setProducts(List<ProductSearchModel> products) {
        this.products = products;
        return this;
    }
}
