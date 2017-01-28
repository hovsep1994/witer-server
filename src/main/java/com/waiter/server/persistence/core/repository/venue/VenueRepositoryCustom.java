package com.waiter.server.persistence.core.repository.venue;

import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * Created by Admin on 1/3/2016.
 */
public interface VenueRepositoryCustom {

    List<Venue> findBySearchParameters(VenueSearchParameters parameters);
}
