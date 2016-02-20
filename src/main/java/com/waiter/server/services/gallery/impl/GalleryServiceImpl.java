package com.waiter.server.services.gallery.impl;

import com.waiter.server.persistence.core.repository.gallery.GalleryRepository;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.gallery.dto.GalleryDto;
import com.waiter.server.services.gallery.model.Gallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author shahenpoghosyan
 */
@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    GalleryImageService galleryImageService;

    public Gallery createGallery(GalleryDto galleryDto) {
        Gallery gallery = new Gallery();
        galleryDto.convertToEntityModel(gallery);
        return galleryRepository.save(gallery);
    }

    public Gallery getGalleryById(Long id){
        return galleryRepository.getOne(id);
    }

}
