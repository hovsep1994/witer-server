package com.waiter.server.services.gallery.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 8:01 PM
 */
public class GalleryImageDto extends AbstractDtoModel<GalleryImage> {

    private String fileName;
    private ImageType imageType;
    private GalleryImageType galleryImageType;

    @Override
    public void convertToEntityModel(GalleryImage galleryImage) {
        galleryImage.setFileName(getFileName());
        galleryImage.setImageType(getImageType());
        galleryImage.setGalleryImageType(getGalleryImageType());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public GalleryImageType getGalleryImageType() {
        return galleryImageType;
    }

    public void setGalleryImageType(GalleryImageType galleryImageType) {
        this.galleryImageType = galleryImageType;
    }
}
