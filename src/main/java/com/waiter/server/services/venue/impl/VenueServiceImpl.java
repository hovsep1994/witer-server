package com.waiter.server.services.venue.impl;

import com.waiter.server.persistence.core.repository.venue.VenueRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.event.VenueLocationUpdateEvent;
import com.waiter.server.services.venue.event.VenueUpdateEvent;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Admin on 12/12/2015.
 */
@Service
public class VenueServiceImpl implements VenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueServiceImpl.class);

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @Override
    public Venue create(VenueDto venueDto) {
        LOGGER.debug("Creating venue by dto -{}", venueDto);
        assertVenueDto(venueDto);
        final Venue venue = new Venue();
        venueDto.updateProperties(venue);
        final Venue createdVenue = venueRepository.save(venue);
        LOGGER.debug("Venue -{} successfully stored", venue);
        applicationEventBus.publishAsynchronousEvent(new VenueUpdateEvent(createdVenue.getId()));
        return createdVenue;
    }

    @Override
    @Transactional
    public Venue updateVenue(Long id, VenueDto venueDto) {
        assertVenueId(id);
        assertVenueDto(venueDto);
        final Venue venue = venueRepository.findOne(id);
        if (venue == null) {
            throw new RuntimeException("venue with id not found");
        }
        if (venue.getLocation() != venueDto.getLocation()) {
            applicationEventBus.publishAsynchronousEvent(new VenueLocationUpdateEvent(id));
        }
        venueDto.updateProperties(venue);
        final Venue updatedVenue = venueRepository.save(venue);
//        applicationEventBus.publishAsynchronousEvent(new VenueUpdateEvent(updatedVenue.getId()));
        return updatedVenue;
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

    private void assertVenueId(Long id) {
        notNull(id, "id must not be null");
    }

    private void assertVenueDto(VenueDto venueDto) {
        notNull(venueDto, "venue dto must not bu null");
        notNull(venueDto.getCompanyId(), "company id must not bu null");
        notNull(venueDto.getLocation(), "location must not bu null");
        notNull(venueDto.getName(), "name must not bu null");
    }
}
