package com.waiter.server.services.gallery.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.gallery.model.Gallery;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 7:42 PM
 */
public class GalleryDto extends AbstractDtoModel<Gallery> {

    private boolean systemGallery;

    public GalleryDto() {
    }

    public GalleryDto(boolean systemGallery) {
        this.systemGallery = systemGallery;
    }

    @Override
    public void convertToEntityModel(Gallery gallery) {
        gallery.setSystemGallery(isSystemGallery());
    }

    public boolean isSystemGallery() {
        return systemGallery;
    }

    public void setSystemGallery(boolean systemGallery) {
        this.systemGallery = systemGallery;
    }
}
