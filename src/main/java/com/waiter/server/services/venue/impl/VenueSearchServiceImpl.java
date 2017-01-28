package com.waiter.server.services.venue.impl;

import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.event.VenueUpdateEvent;
import com.waiter.server.services.venue.event.VenueUpdateEventListener;
import com.waiter.server.services.venue.model.Venue;
import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import com.waiter.server.solr.core.repository.venue.VenueSolrRepository;
import com.waiter.server.solr.core.repository.venue.model.VenueDocument;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

/**
 * Created by hovsep on 8/6/16.
 */
@Service
public class VenueSearchServiceImpl implements VenueSearchService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueSearchServiceImpl.class);

    @Autowired
    private VenueSolrRepository venueSolrRepository;

    @Autowired
    private VenueService venueService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationEventBus.subscribe(venueUpdateEventListener);
    }

    @Override
    public void addOrUpdate(Venue venue) {
        notNull(venue);
        final VenueSolrDocument venueSolrDocument = new VenueSolrDocument();
        venueSolrDocument.setId(venue.getId().toString());
        venueSolrDocument.setName(venue.getName());
        venueSolrDocument.setCompanyId(venue.getCompany().getId());
        final SolrLocation location = new SolrLocation(venue.getLocation().getLatitude(), venue.getLocation().getLongitude());
        venueSolrDocument.setLocation(location);
        venueSolrRepository.save(venueSolrDocument);
    }

    @Override
    public List<Venue> getVenuesBySearchParameters(VenueSearchParameters parameters) {
        LOGGER.debug("Finding for search params -{}", parameters);
        notNull(parameters, "parameters must not be null");
        final Point point = new Point(parameters.getLatitude(), parameters.getLongitude());
        final List<VenueDocument> venueSolrDocuments = venueSolrRepository.findBySearchParameters(parameters.getName(), point);
        final List<Venue> venues = venueSolrDocuments.stream()
                .map(venueSolrDocument -> venueService.getById(Long.valueOf(venueSolrDocument.getId())))
                .collect(Collectors.toList());
        LOGGER.debug("Successfully find venues -{} for search params -{}", venues, parameters);
        return new ArrayList<>();
    }

    private final VenueUpdateEventListener venueUpdateEventListener = new VenueUpdateEventListener() {
        @Override
        public void processVenueUpdatedEvent(VenueUpdateEvent venueUpdateEvent) {
            addOrUpdate(venueUpdateEvent.getVenue());
        }
    };
}
