package com.waiter.server.commons.entities;

/**
 * @author shahenpoghosyan
 */
public class Gallery {

    private Long id;
    private Long entityId;
    private String path;
    private String extension;
    private ImageType imageType;

    public Gallery(Long entityId, String extension) {
        this.entityId = entityId;
        this.extension = extension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public enum ImageType {
        PRODUCT_IMAGE,
        GROUP_IMAGE
    }
}
