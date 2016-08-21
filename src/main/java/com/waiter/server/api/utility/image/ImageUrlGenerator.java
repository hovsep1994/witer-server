package com.waiter.server.api.utility.image;

import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by hovsep on 8/21/16.
 */
@Service
public class ImageUrlGenerator {

    private static String cdnBaseUrl;

    @Value("#{appProperties['cdn.base.url']}")
    public void setPrivateName(String privateName) {
        cdnBaseUrl = privateName;
    }

    public static String getFullMainUrl(EntityType entityType, Set<GalleryImage> images) {
        return getFullUrl(entityType, images, GalleryImageType.MAIN);
    }

    public static String getFullUrl(EntityType entityType, Set<GalleryImage> images, GalleryImageType galleryImageType) {
        final GalleryImage galleryImage = images.stream()
                .filter(image -> image.getGalleryImageType() == galleryImageType)
                .findFirst().orElse(null);
        return getFullUrl(entityType, galleryImage);
    }

    public static String getFullUrl(EntityType entityType, GalleryImage galleryImage) {
        final String imageUrl = galleryImage != null ? galleryImage.getUrl() : entityType.getDefaultImageUrl();
        return cdnBaseUrl + imageUrl;
    }
}
