package com.waiter.server.persistence.core.repository.identity.foursquare;

import com.waiter.server.services.identity.model.foursquare.FoursquareIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hovsep on 5/15/16.
 */
public interface FoursquareIdentityRepository extends JpaRepository<FoursquareIdentity, Long> {
}
