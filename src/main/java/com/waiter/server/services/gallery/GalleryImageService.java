package com.waiter.server.services.gallery;

import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.GalleryImage;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 7:08 PM
 */
public interface GalleryImageService {

    GalleryImage save(Long galleryId, GalleryImageDto galleryImageDto, InputStream imageStream) throws IOException, ServiceException;
}
