package com.waiter.server.services.venue;


import com.waiter.server.services.venue.model.Venue;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface VenueService {

    Venue create(Venue venue);

    Venue get(Long id);

    List<Venue> get(String name, double lat, double lon);
}
