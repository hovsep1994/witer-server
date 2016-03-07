package com.waiter.server.services.venue;


import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface VenueService {

    Venue create(VenueDto venueDto);

    Venue updateVenue(Long id, VenueDto venueDto);

    Venue getVenueById(Long id);

    List<Venue> getVenuesBySearchParameters(VenueSearchParameters parameters);
}
