package com.waiter.server.services.gallery.impl;

import com.waiter.server.persistence.core.repository.gallery.GalleryRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.filesystem.FileSystemService;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.gallery.dto.GalleryDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author shahenpoghosyan
 */
@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Override
    public Gallery createGallery(GalleryDto galleryDto) {
        Gallery gallery = new Gallery();
        galleryDto.convertToEntityModel(gallery);
        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery getGalleryById(Long id) throws ServiceException {
        Gallery gallery = galleryRepository.findOne(id);
        if (gallery == null) {
            throw new ServiceException(ErrorCode.NOT_FOUND, "");
        }
        return gallery;
    }
}
