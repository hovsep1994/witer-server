package com.waiter.server.db;

import com.waiter.server.commons.entities.Venue;

import java.util.List;

public interface VenueDAO {
    int create(Venue venue);

    Venue get(int id);

    List<Venue> get(String name, double lat, double lon);

}
