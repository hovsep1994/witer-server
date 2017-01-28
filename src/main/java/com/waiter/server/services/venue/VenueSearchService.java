package com.waiter.server.services.venue;

import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * Created by hovsep on 8/6/16.
 */
public interface VenueSearchService {

    void addOrUpdate(Venue venue);

    List<Venue> getVenuesBySearchParameters(VenueSearchParameters parameters);

}
