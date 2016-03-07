package com.waiter.server.services.gallery.impl;

import com.waiter.server.persistence.core.repository.gallery.GalleryImageRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.filesystem.PhotoSaverService;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 7:08 PM
 */
@Service
public class GalleryImageServiceImpl implements GalleryImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryImageServiceImpl.class);

    @Autowired
    private GalleryImageRepository galleryImageRepository;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private PhotoSaverService photoSaverService;

    public GalleryImage getGalleryImageById(Long id) {
        assertGalleryImageId(id);
        GalleryImage galleryImage = galleryImageRepository.findOne(id);
        if (galleryImage == null) {
            LOGGER.error("gallery image -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "gallery image not found");
        }
        return galleryImage;
    }

    @Transactional
    @Override
    public GalleryImage addImage(Long galleryId, GalleryImageDto galleryImageDto, InputStream inputStream) throws ServiceException {
        Assert.notNull(galleryId, "gallery id must not be null");
        Assert.notNull(inputStream, "inputStream must not be null");

        GalleryImage galleryImage = new GalleryImage();
        galleryImageDto.convertToEntityModel(galleryImage);
        Gallery gallery = galleryService.getGalleryById(galleryId);
        galleryImage.setGallery(gallery);
        galleryImage = galleryImageRepository.save(galleryImage);
        String filePath = generatePathForGallery(galleryImage);
        try {
            photoSaverService.savePhoto(inputStream, filePath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IOException, e.getMessage());
        }
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

    private void assertGalleryImageId(Long id) {
        Assert.notNull(id, "gallery image id must not be null");
    }

}
