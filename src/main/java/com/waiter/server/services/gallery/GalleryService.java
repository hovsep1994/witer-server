package com.waiter.server.services.gallery;

import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.dto.GalleryDto;
import com.waiter.server.services.gallery.model.Gallery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
public interface GalleryService {

    Gallery createGallery(GalleryDto galleryDto);

    Gallery getGalleryById(Long id) throws ServiceException;
}
