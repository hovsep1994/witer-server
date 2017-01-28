package com.waiter.server.services.gallery.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Admin on 2/6/2016.
 */
@Entity
@Table(name = "gallery")
public class Gallery extends AbstractEntityModel {

    @Column(name = "system_gallery", nullable = false)
    private boolean systemGallery;

    @OneToMany(mappedBy = "gallery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GalleryImage> galleryImages;

    public Gallery() {
        this.galleryImages = new LinkedHashSet<>();
        this.systemGallery = false;
    }

    public boolean isSystemGallery() {
        return systemGallery;
    }

    public void setSystemGallery(boolean systemGallery) {
        this.systemGallery = systemGallery;
    }

    public Set<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(Set<GalleryImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gallery gallery = (Gallery) o;

        if (systemGallery != gallery.systemGallery) return false;
        return !(galleryImages != null ? !galleryImages.equals(gallery.galleryImages) : gallery.galleryImages != null);

    }

    @Override
    public int hashCode() {
        int result = (systemGallery ? 1 : 0);
        result = 31 * result + (galleryImages != null ? galleryImages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "systemGallery=" + systemGallery +
                ", galleryImages=" + galleryImages +
                '}';
    }
}
