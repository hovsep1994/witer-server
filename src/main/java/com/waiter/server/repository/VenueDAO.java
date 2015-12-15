package com.waiter.server.repository;

import com.waiter.server.commons.entities.Venue;

import java.util.List;

public interface VenueDAO {
    Venue create(Venue venue);

    Venue get(int id);

    List<Venue> get(String name, double lat, double lon);

}
