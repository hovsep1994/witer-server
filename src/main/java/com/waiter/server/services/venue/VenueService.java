package com.waiter.server.services.venue;


import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.model.Venue;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 12/12/2015.
 */
public interface VenueService {

    Venue create(VenueDto venueDto);

    Venue update(Long id, String name, Long menuId);

    Venue getById(Long id);

    GalleryImage addImage(Long venueId, InputStream inputStream) throws ServiceException;

    List<Venue> attacheMenuToVenues(Set<Long> venueIds, Long menuId);

    Venue updateAttachmentOfMenu(Long venueId, Long menuId);

    void delete(Long venueId);
}
