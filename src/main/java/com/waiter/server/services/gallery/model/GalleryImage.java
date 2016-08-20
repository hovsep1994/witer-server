package com.waiter.server.services.gallery.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.*;

/**
 * Created by Admin on 12/24/2015.
 */
@Entity
@Table(name = "gallery_image")
public class GalleryImage extends AbstractEntityModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "url", nullable = true)
    private String url;

    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    @Column(name = "gallery_image_type", nullable = false)
    private GalleryImageType galleryImageType;

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
