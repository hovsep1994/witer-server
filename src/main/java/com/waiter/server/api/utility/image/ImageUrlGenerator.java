package com.waiter.server.api.utility.image;

import com.waiter.server.services.gallery.model.Gallery;
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

    public static String getUrl(EntityType entityType, Gallery gallery) {
        return getUrl(entityType, gallery.getGalleryImages(), GalleryImageType.MAIN, true);
    }

    public static String getUrl(EntityType entityType, Gallery gallery, boolean returnDefault) {
        return getUrl(entityType, gallery.getGalleryImages(), GalleryImageType.MAIN, returnDefault);
    }

    public static String getUrl(EntityType entityType, Gallery gallery, GalleryImageType galleryImageType, boolean returnDefault) {
        return getUrl(entityType, gallery.getGalleryImages(), galleryImageType, returnDefault);
    }

    public static String getUrl(EntityType entityType, Gallery gallery, GalleryImageType galleryImageType) {
        return getUrl(entityType, gallery.getGalleryImages(), galleryImageType, true);
    }

    private static String getUrl(EntityType entityType, Set<GalleryImage> images, GalleryImageType galleryImageType, boolean returnDefault) {
        final GalleryImage galleryImage = images.stream()
                .filter(image -> image.getGalleryImageType() == galleryImageType)
                .findFirst().orElse(null);
        return getUrl(entityType, galleryImage, returnDefault);
    }

    public static String getUrl(EntityType entityType, GalleryImage galleryImage, boolean returnDefault) {
        if (galleryImage == null && !returnDefault) {
            return null;
        }
        final String imageUrl = galleryImage != null ? galleryImage.getUrl() : entityType.getDefaultImageUrl();
        return cdnBaseUrl + imageUrl;
    }

    public static String getUrl(EntityType entityType, GalleryImage galleryImage) {
        return getUrl(entityType, galleryImage, true);
    }
}
