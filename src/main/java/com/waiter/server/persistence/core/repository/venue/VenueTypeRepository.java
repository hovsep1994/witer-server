package com.waiter.server.persistence.core.repository.venue;

import com.waiter.server.services.venue.model.VenueType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hovsep on 9/11/16.
 */
public interface VenueTypeRepository extends JpaRepository<VenueType, Long> {
}
