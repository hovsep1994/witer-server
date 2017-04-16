package com.waiter.server.persistence.core.repository.venue;

import com.waiter.server.services.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long>, VenueRepositoryCustom {

    Venue findBySource(String source);
}
