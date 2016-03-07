package com.waiter.server.services.gallery.impl;

import com.waiter.server.persistence.core.repository.gallery.GalleryRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.gallery.model.Gallery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author shahenpoghosyan
 */
@Service
public class GalleryServiceImpl implements GalleryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryServiceImpl.class);

    @Autowired
    private GalleryRepository galleryRepository;

    @Override
    public Gallery createGallery(boolean isSystemGallery) {
        Gallery gallery = new Gallery();
        gallery.setSystemGallery(isSystemGallery);
        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery getGalleryById(Long id) {
        Gallery gallery = galleryRepository.findOne(id);
        if (gallery == null) {
            LOGGER.error("gallery -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "gallery not found");
        }
        return gallery;
    }
}
