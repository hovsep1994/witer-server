package com.waiter.server.persistence.core.repository.venue;

import com.waiter.server.services.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    //TODO need to write custom query
    List<Venue> findByName(String name, double lat, double lon);

}
