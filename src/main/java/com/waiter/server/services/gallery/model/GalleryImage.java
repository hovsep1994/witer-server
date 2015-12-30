package com.waiter.server.services.gallery.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Admin on 12/24/2015.
 */
public class GalleryImage extends AbstractEntityModel {

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    @Column(name = "gallery_image_type", nullable = false)
    private GalleryImageType galleryImageType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GalleryImage that = (GalleryImage) o;

        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (imageType != that.imageType) return false;
        return galleryImageType == that.galleryImageType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (imageType != null ? imageType.hashCode() : 0);
        result = 31 * result + (galleryImageType != null ? galleryImageType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GalleryImage{" +
                "fileName='" + fileName + '\'' +
                ", imageType=" + imageType +
                ", galleryImageType=" + galleryImageType +
                "} " + super.toString();
    }
}
