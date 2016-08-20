package com.waiter.server.services.venue.impl;

import com.waiter.server.persistence.core.repository.venue.VenueRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.product.model.Product;
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

import java.io.InputStream;

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
    private LocationService locationService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @Autowired
    private GalleryImageService galleryImageService;

    @Override
    @Transactional
    public Venue create(VenueDto venueDto, Location location) {
        LOGGER.debug("Creating venue by dto -{}", venueDto);
        assertVenueDto(venueDto);
        final Venue venue = new Venue();
        venueDto.updateProperties(venue);
        venue.setLocation(locationService.createLocation(location));
        venue.setGallery(new Gallery());
        final Venue createdVenue = venueRepository.save(venue);
        LOGGER.debug("Venue -{} successfully stored", venue);
        applicationEventBus.publishAsynchronousEvent(new VenueUpdateEvent(createdVenue.getId()));
        return createdVenue;
    }

    @Override
    @Transactional
    public Venue updateVenue(Long id, VenueDto venueDto, Location location) {
        assertVenueId(id);
        assertVenueDto(venueDto);
        final Venue venue = venueRepository.findOne(id);
        if (venue == null) {
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "venue with id not found");
        }
        if (location != null) {
            if (!venue.getLocation().getId().equals(location.getId())) {
                throw new ServiceRuntimeException(ErrorCode.NOT_MATCH, "venue location not match");
            }
            if (venue.getLocation() != location) {
                locationService.updateLocation(location);
                applicationEventBus.publishAsynchronousEvent(new VenueLocationUpdateEvent(id));
            }
        }
        venueDto.updateProperties(venue);
        final Venue updatedVenue = venueRepository.save(venue);
        applicationEventBus.publishAsynchronousEvent(new VenueUpdateEvent(updatedVenue.getId()));
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

    @Override
    public GalleryImage addImage(Long venueId, InputStream inputStream) throws ServiceException {
        Venue venue = getVenueById(venueId);
        final GalleryImageDto galleryImageDto = new GalleryImageDto();
        galleryImageDto.setGalleryImageType(GalleryImageType.MAIN);
        galleryImageDto.setImageType(ImageType.JPEG);
        galleryImageDto.setFileName("venue");
        if (venue.getGallery() == null) {
            venue.setGallery(new Gallery());
            venue = venueRepository.save(venue);
        }
        final GalleryImage galleryImage = galleryImageService.addImage(venue.getGallery().getId(), galleryImageDto, inputStream);
        return galleryImage;
    }

    @Override
    public void delete(Long venueId) {
        venueRepository.delete(venueId);
    }

    private void assertVenueId(Long id) {
        notNull(id, "id must not be null");
    }

    private void assertVenueDto(VenueDto venueDto) {
        notNull(venueDto, "venue dto must not bu null");
        notNull(venueDto.getCompanyId(), "company id must not bu null");
        notNull(venueDto.getName(), "name must not bu null");
    }
}
