package com.waiter.server.api.venue;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.venue.model.VenueModel;
import com.waiter.server.api.venue.model.request.VenueRequest;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.user.model.User;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MenuKitResponseEntity<VenueModel> findOne(@PathVariable Long id) {
        Venue venue = venueService.getVenueById(id);
        VenueModel venueModel = VenueModel.convert(venue, cdnBaseUrl);
        return MenuKitResponseEntity.success2(venueModel);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public MenuKitResponseEntity<VenueModel> createVenue(@RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        final Location location = LocationModel.convert(venueRequest.getLocation());
        final VenueDto venueDto = venueRequest.convertToVenueDto();
        final Venue createdVenue = venueService.create(venueDto, location, user.getCompany().getId());
        final VenueModel venueModel = VenueModel.convert(createdVenue, cdnBaseUrl);
        return MenuKitResponseEntity.success2(venueModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MenuKitResponseEntity<VenueModel> updateVenue(@PathVariable Long id, @RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        final Location location = LocationModel.convert(venueRequest.getLocation());
        final Venue createdVenue = venueService.updateVenue(id, venueRequest.convertToVenueDto(), location);
        final VenueModel venueModel = VenueModel.convert(createdVenue, cdnBaseUrl);
        return MenuKitResponseEntity.success2(venueModel);
    }

    @RequestMapping(value = "/{venueId}", method = RequestMethod.DELETE)
    public MenuKitResponseEntity<Void> delete(@PathVariable Long venueId,  @ModelAttribute User user) throws ServiceException {
        checkUserPermission(user, venueId);
        venueService.delete(venueId);
        return MenuKitResponseEntity.success2();
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public MenuKitResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file, @PathVariable Long id, @ModelAttribute User user) throws ServiceException {
        checkUserHasAccess(user, venueService.getVenueById(id).getCompany());
        try {
            final GalleryImage galleryImage = venueService.addImage(id, file.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return MenuKitResponseEntity.success2("ok");
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
