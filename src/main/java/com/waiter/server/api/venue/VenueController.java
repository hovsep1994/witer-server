package com.waiter.server.api.venue;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.ResponseEntity;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/venue")
public class VenueController extends AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueController.class);

    @Autowired
    private VenueService venueService;

    @Autowired
    private VenueSearchService venueSearchService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<VenueModel> findOne(@PathVariable Long id) {
        Venue venue = venueService.getVenueById(id);
        VenueModel venueModel = VenueModel.convert(venue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<VenueModel> createVenue(@RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        final Location location = LocationModel.convert(venueRequest.getLocation());
        final VenueDto venueDto = venueRequest.convertToVenueDto();
        venueDto.setCompanyId(user.getCompany().getId());
        final Venue createdVenue = venueService.create(venueDto, location);
        final VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<VenueModel> updateVenue(@PathVariable Long id, @RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        final Location location = LocationModel.convert(venueRequest.getLocation());
        final Venue createdVenue = venueService.updateVenue(id, venueRequest.convertToVenueDto(), location);
        final VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(HttpServletRequest request, @PathVariable Long id, @ModelAttribute User user) throws ServiceException {
        checkUserHasAccess(user, venueService.getVenueById(id).getCompany());
        try {
            final GalleryImage galleryImage = venueService.addImage(id, request.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return ResponseEntity.success("ok");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> findVenues(@RequestBody VenueSearchParameters parameters) {
        List<Venue> venues = venueSearchService.getVenuesBySearchParameters(parameters);
        return ResponseEntity.success(venues);
    }

}
