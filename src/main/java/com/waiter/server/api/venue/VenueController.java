package com.waiter.server.api.venue;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.venue.model.VenueModel;
import com.waiter.server.api.venue.model.request.VenueRequest;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.user.model.User;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/venue")
public class VenueController extends MainController {

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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<VenueModel> createVenue(@RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        checkUserHasAccess(user, companyService.get(venueRequest.getCompanyId()));
        final Location location = LocationModel.convert(venueRequest.getLocation());
        final Venue createdVenue = venueService.create(venueRequest.convertToVenueDto(), location);
        final VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public ResponseEntity<VenueModel> updateVenue(@PathVariable Long id, @RequestBody VenueRequest venueRequest, @ModelAttribute User user) {
        checkUserHasAccess(user, companyService.get(venueRequest.getCompanyId()));
        final Location location = LocationModel.convert(venueRequest.getLocation());
        final Venue createdVenue = venueService.updateVenue(id, venueRequest.convertToVenueDto(), location);
        final VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> findVenues(@RequestBody VenueSearchParameters parameters) {
        List<Venue> venues = venueSearchService.getVenuesBySearchParameters(parameters);
        return ResponseEntity.success(venues);
    }

}
