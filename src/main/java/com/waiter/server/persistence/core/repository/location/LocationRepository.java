package com.waiter.server.persistence.core.repository.location;

import com.waiter.server.services.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hovsep on 8/7/16.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
}
