package com.waiter.server.services.venue;

import com.waiter.server.commons.entities.Venue;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface VenueService {

    Venue create(Venue venue);

    Venue get(int id);

    List<Venue> get(String name, double lat, double lon);
}
