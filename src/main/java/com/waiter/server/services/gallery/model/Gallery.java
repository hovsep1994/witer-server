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

    @OneToMany(mappedBy = "gallery", fetch = FetchType.LAZY)
    private Set<GalleryImage> images;

    public Gallery() {
        this.images = new LinkedHashSet<>();
        this.systemGallery = false;
    }

    public boolean isSystemGallery() {
        return systemGallery;
    }

    public void setSystemGallery(boolean systemGallery) {
        this.systemGallery = systemGallery;
    }

    public Set<GalleryImage> getImages() {
        return images;
    }

    public void setImages(Set<GalleryImage> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gallery gallery = (Gallery) o;

        if (systemGallery != gallery.systemGallery) return false;
        return !(images != null ? !images.equals(gallery.images) : gallery.images != null);

    }

    @Override
    public int hashCode() {
        int result = (systemGallery ? 1 : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "systemGallery=" + systemGallery +
                ", images=" + images +
                '}';
    }
}
