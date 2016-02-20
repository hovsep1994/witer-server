package com.waiter.server.services.gallery;

import com.waiter.server.services.gallery.dto.GalleryDto;
import com.waiter.server.services.gallery.model.Gallery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
@Service
public interface GalleryService {

    Gallery createGallery(GalleryDto galleryDto);

    Gallery getGalleryById(Long id);
}
