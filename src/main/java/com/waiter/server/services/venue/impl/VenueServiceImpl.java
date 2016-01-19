package com.waiter.server.services.venue.impl;

import com.waiter.server.persistence.core.repository.venue.VenueRepository;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public Venue getVenueById(Long id) {
        Assert.notNull(id, "id must not be null");
        Venue venue = venueRepository.findOne(id);

        if (venue == null) {
            throw new ServiceRuntimeException("Venue with id -" + id + " not found");
        }

        return venue;
    }

    @Override
    public List<Venue> getVenuesBySearchParameters(VenueSearchParameters parameters) {
        Assert.notNull(parameters, "parameters must not be null");
        return venueRepository.findBySearchParameters(parameters);
    }
}
