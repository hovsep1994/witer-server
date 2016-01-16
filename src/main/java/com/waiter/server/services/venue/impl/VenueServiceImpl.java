package com.waiter.server.services.venue.impl;

import com.waiter.server.persistence.core.repository.venue.VenueRepository;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Override
    public Venue create(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public Venue get(Long id) {
        return venueRepository.findOne(id);
    }

    @Override
    public List<Venue> get(String name, double lat, double lon) {
        return venueRepository.findByName(name, lat, lon);
    }
}
