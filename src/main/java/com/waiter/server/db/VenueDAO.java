package com.waiter.server.db;

import com.waiter.server.commons.entities.Venue;

public interface VenueDAO {
    int create(Venue venue);
    Venue get(int id);
}
