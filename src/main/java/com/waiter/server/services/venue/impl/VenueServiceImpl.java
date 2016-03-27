package com.waiter.server.services.venue.impl;

import com.waiter.server.persistence.core.repository.venue.VenueRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@Service
public class VenueServiceImpl implements VenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueServiceImpl.class);

    @Autowired
    private VenueRepository venueRepository;

    @Override
    public Venue create(VenueDto venueDto) {
        assertVenueDto(venueDto);
        Venue venue = new Venue();
        venueDto.updateProperties(venue);
        return venueRepository.save(venue);
    }

    @Override
    public Venue updateVenue(Long id, VenueDto venueDto) {
        assertVenueId(id);
        assertVenueDto(venueDto);
        Venue venue = venueRepository.findOne(id);
        if (venue == null) {
            throw new RuntimeException("venue with id not found");
        }
        venueDto.updateProperties(venue);
        return venueRepository.save(venue);
    }

    @Override
    public Venue getVenueById(Long id) {
        assertVenueId(id);
        Venue venue = venueRepository.findOne(id);
        if (venue == null) {
            LOGGER.error("Venue with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Venue not found");
        }
        return venue;
    }

    @Override
    public List<Venue> getVenuesBySearchParameters(VenueSearchParameters parameters) {
        Assert.notNull(parameters, "parameters must not be null");
        return venueRepository.findBySearchParameters(parameters);
    }

    private void assertVenueId(Long id) {
        Assert.notNull(id, "id must not be null");
    }

    private void assertVenueDto(VenueDto venueDto) {
        Assert.notNull(venueDto);
        Assert.notNull(venueDto.getCompanyId());
        Assert.notNull(venueDto.getMenuId());
        Assert.notNull(venueDto.getLocation());
        Assert.notNull(venueDto.getName());
    }
}
