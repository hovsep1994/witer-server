package com.waiter.server.services.venue.impl;

import com.waiter.server.commons.entities.Venue;
import com.waiter.server.repository.VenueDAO;
import com.waiter.server.services.venue.VenueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public class VenueServiceImpl implements VenueService {

    @Autowired
    VenueDAO venueDAO;

    @Override
    public Venue create(Venue venue) {
        return venueDAO.create(venue);
    }

    @Override
    public Venue get(int id) {
        return venueDAO.get(id);
    }

    @Override
    public List<Venue> get(String name, double lat, double lon) {
        return venueDAO.get(name, lat, lon);
    }
}
