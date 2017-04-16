package com.waiter.server.services.venue;


import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
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

    List<Venue> getAllByIds(List<Long> ids);

    GalleryImage addImage(Long venueId, InputStream inputStream, GalleryImageType galleryImageType, ImageType imageType) throws ServiceException;

    GalleryImage addImage(Long venueId, InputStream inputStream) throws ServiceException;

    List<Venue> attacheMenuToVenues(Set<Long> venueIds, Long menuId);

    Venue updateAttachmentOfMenu(Long venueId, Long menuId);

    void delete(Long venueId);

    List<Venue> getVenues(int page, int size);
}
