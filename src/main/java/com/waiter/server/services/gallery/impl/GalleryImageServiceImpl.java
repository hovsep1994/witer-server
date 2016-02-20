package com.waiter.server.services.gallery.impl;

import com.waiter.server.persistence.core.repository.gallery.GalleryImageRepository;
import com.waiter.server.services.filesystem.FileSystemService;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.gallery.dto.GalleryDto;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 7:08 PM
 */
@Service
public class GalleryImageServiceImpl implements GalleryImageService {

    private static final int[] productImageSizes = {60, 120};

    @Autowired
    private GalleryImageRepository galleryImageRepository;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private FileSystemService fileSystemService;

    public GalleryImage getGalleryImageById(Long id) {
        return galleryImageRepository.findOne(id);
    }

    @Transactional
    @Override
    public GalleryImage save(Long galleryId, GalleryImageDto galleryImageDto, InputStream imageStream) throws IOException {
        GalleryImage galleryImage = new GalleryImage();
        galleryImageDto.convertToEntityModel(galleryImage);
        Gallery gallery = galleryService.getGalleryById(galleryId);
        galleryImage.setGallery(gallery);
        galleryImage = galleryImageRepository.save(galleryImage);
        String filePath = generatePathForGallery(galleryImage);
        fileSystemService.saveImage(filePath, imageStream, productImageSizes);
        return galleryImage;
    }

    public void deleteGalleryImageById(Long id) {
        //TODO delete the file too
        galleryImageRepository.delete(id);
    }

    private String generatePathForGallery(GalleryImage image) {
        return new StringBuilder()
                .append(image.getFileName())
                .append("/").append(image.getId())
                .append(".").append(ImageType.identifyImageType(image.getImageType().name())).toString();
    }

}
