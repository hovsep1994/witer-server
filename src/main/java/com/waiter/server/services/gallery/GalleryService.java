package com.waiter.server.services.gallery;

import com.waiter.server.services.gallery.model.Gallery;

/**
 * @author shahenpoghosyan
 */
public interface GalleryService {

    public Gallery createGallery(boolean isSystemGallery);

    Gallery getGalleryById(Long id);
}
