package com.waiter.server.api.venue;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.venue.model.VenueModel;
import com.waiter.server.api.venue.model.request.AddVenueRequest;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.user.model.User;
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
    private CompanyService companyService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<VenueModel> findOne(@PathVariable Long id) {
        Venue venue = venueService.getVenueById(id);
        VenueModel venueModel = VenueModel.convert(venue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<VenueModel> createMenu(@RequestBody AddVenueRequest addVenueRequest, @ModelAttribute User user) {
        checkUserHasAccess(user, companyService.get(addVenueRequest.getCompanyId()));
        VenueDto venueDto = new VenueDto();
        venueDto.setMenuId(addVenueRequest.getMenuId());
        venueDto.setCompanyId(addVenueRequest.getCompanyId());
        venueDto.setLocation(LocationModel.convert(addVenueRequest.getLocation()));
        Venue createdVenue = venueService.create(venueDto);
        VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> findVenues(@RequestBody VenueSearchParameters parameters) {
        List<Venue> venues = venueService.getVenuesBySearchParameters(parameters);
        return ResponseEntity.success(venues);
    }

}
