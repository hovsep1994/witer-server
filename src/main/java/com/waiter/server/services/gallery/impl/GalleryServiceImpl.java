package com.waiter.server.services.gallery.impl;

import com.waiter.server.persistence.core.repository.gallery.GalleryRepository;
import com.waiter.server.services.filesystem.FileSystemService;
import com.waiter.server.services.gallery.GalleryService;
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

//    private static final int[] productImageSizes = {60, 120};
//
//    @Autowired
//    private GalleryRepository galleryRepository;
//    @Autowired
//    private FileSystemService fileSystemService;
//
//    public GalleryImage save(GalleryImage image, InputStream imageStream) throws IOException {
//        image.setFileName(generatePathForGallery(image));
//        fileSystemService.saveImage(image.getFileName(), imageStream, productImageSizes);
//        return galleryRepository.save(image);
//    }
//
//    private String generatePathForGallery(GalleryImage image) {
//        return new StringBuilder()
//                .append(decideEntityPath(image.getImageType()))
//                .append("/").append(image.getEntityId())
//                .append(".").append(image.getExtension()).toString();
//    }
//
//    private String decideEntityPath(GalleryImageType imageType) {
//        switch (imageType) {
//            case PRODUCT_IMAGE:
//                return "/products";
//            case GROUP_IMAGE:
//                return "/groups";
//            default:
//                throw new RuntimeException("Unsupported image type");
//        }
//    }

}
