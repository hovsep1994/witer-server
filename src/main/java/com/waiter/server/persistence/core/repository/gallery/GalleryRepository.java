package com.waiter.server.persistence.core.repository.gallery;

import com.waiter.server.services.gallery.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 12/27/2015.
 */
@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
