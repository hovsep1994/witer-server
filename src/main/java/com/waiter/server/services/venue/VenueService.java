package com.waiter.server.services.venue;


import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface VenueService {

    Venue create(VenueDto venueDto, Location location);

    Venue updateVenue(Long id, VenueDto venueDto, Location location);

    Venue getVenueById(Long id);

    GalleryImage addImage(Long venueId, InputStream inputStream) throws ServiceException;

    void delete(Long venueId);
}
