package com.waiter.server.api.venue;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.api.venue.model.VenueModel;
import com.waiter.server.api.venue.model.request.VenueRequest;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.user.model.User;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.event.VenueLocationUpdateEvent;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/venue")
public class VenueController extends AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueController.class);

    @Autowired
    private VenueService venueService;

    @Value("#{appProperties['cdn.base.url']}")
    private String cdnBaseUrl;

    @Autowired
    private VenueSearchService venueSearchService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable Long id) {
        Venue venue = venueService.getById(id);
        VenueModel venueModel = VenueModel.convert(venue, cdnBaseUrl);
        return MenuKitResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createVenue(@RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        final LocationDto locationDto = LocationModel.convertToDto(venueRequest.getLocation());
        final Location location = locationService.create(locationDto);
        final Venue createdVenue = venueService.create(venueRequest.getName(), venueRequest.getMenuId(), location.getId(), user.getCompany().getId());
        final VenueModel venueModel = VenueModel.convert(createdVenue, cdnBaseUrl);
        return MenuKitResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateVenue(@PathVariable Long id, @RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        if (venueRequest.getLocation() != null) {
            final Location location = venueService.getById(id).getLocation();
            final LocationDto locationDto = LocationModel.convertToDto(venueRequest.getLocation());
            locationService.update(location.getId(), locationDto);
            applicationEventBus.publishAsynchronousEvent(new VenueLocationUpdateEvent(id));
        }
        final Venue createdVenue = venueService.update(id, venueRequest.getName(), venueRequest.getMenuId());
        final VenueModel venueModel = VenueModel.convert(createdVenue, cdnBaseUrl);
        return MenuKitResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/{venueId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long venueId, @ModelAttribute User user) throws ServiceException {
        checkUserPermission(user, venueId);
        venueService.delete(venueId);
        return MenuKitResponseEntity.success();
    }

    @RequestMapping(value = "/{venueId}/image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file, @PathVariable Long venueId, @ModelAttribute User user) throws ServiceException {
        checkUserHasAccess(user, venueService.getById(venueId).getCompany());
        GalleryImage galleryImage;
        try {
            galleryImage = venueService.addImage(venueId, file.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return MenuKitResponseEntity.success(ImageUrlGenerator.getUrl(EntityType.VENUE, galleryImage));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public MenuKitResponseEntity<List<VenueModel>> findUserVenues(@ModelAttribute User user) {
        List<VenueModel> venues = user.getCompany().getVenues().stream()
                .map(v -> VenueModel.convert(v, cdnBaseUrl)).collect(Collectors.toList());
        return MenuKitResponseEntity.success2(venues);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public MenuKitResponseEntity<List<Venue>> findVenues(@RequestBody VenueSearchParameters parameters) {
        List<Venue> venues = venueSearchService.getVenuesBySearchParameters(parameters);
        return MenuKitResponseEntity.success2(venues);
    }

    public void checkUserPermission(User user, Long venueId) throws ServiceException {
        validatePermission(user.getCompany().getVenues().stream().map(Venue::getId)
                .collect(Collectors.toList()).contains(venueId));
    }

}
