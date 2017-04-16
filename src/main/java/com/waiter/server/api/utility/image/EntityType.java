package com.waiter.server.api.utility.image;

/**
 * Created by hovsep on 8/21/16.
 */
public enum EntityType {

    VENUE("images/venue-image.png"),
    PRODUCT("product/product-image.png"),
    CATEGORY("product/product-image.png");

    private String defaultImageUrl;

    EntityType(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
    }

    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }
}
