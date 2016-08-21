package com.waiter.server.api.utility;

/**
 * Created by hovsep on 8/21/16.
 */
public enum EntityType {

    VENUE("images/venue-image.png"),
    PRODUCT("images/product-image.png");

    private String defaultImageUrl;

    EntityType(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
    }

    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }
}
