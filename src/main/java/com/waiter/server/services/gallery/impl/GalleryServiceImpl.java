package com.waiter.server.services.gallery.impl;

import com.waiter.server.commons.entities.Gallery;
import com.waiter.server.repository.sql.GalleryRepository;
import com.waiter.server.services.filesystem.FileSystemService;
import com.waiter.server.services.gallery.GalleryService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static com.waiter.server.commons.entities.Gallery.ImageType;

/**
 * @author shahenpoghosyan
 */
@Service
public class GalleryServiceImpl implements GalleryService {

    private static final int[] productImageSizes = {60, 120};

    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private FileSystemService fileSystemService;

    public Gallery save(Gallery gallery, InputStream imageStream) throws IOException {
        gallery.setPath(generatePathForGallery(gallery));
        fileSystemService.saveImage(gallery.getPath(), imageStream, productImageSizes);
        return galleryRepository.save(gallery);
    }

    private String generatePathForGallery(Gallery gallery) {
        return new StringBuilder()
                .append(decideEntityPath(gallery.getImageType()))
                .append("/").append(gallery.getEntityId())
                .append(".").append(gallery.getExtension()).toString();
    }

    private String decideEntityPath(ImageType imageType) {
        switch (imageType) {
            case PRODUCT_IMAGE: return "/products";
            case GROUP_IMAGE: return "/groups";
            default: throw new RuntimeException("Unsupported image type");
        }
    }

}
