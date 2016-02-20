package com.waiter.server.persistence.core.repository.gallery;

import com.waiter.server.services.gallery.model.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 7:08 PM
 */
public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
}
