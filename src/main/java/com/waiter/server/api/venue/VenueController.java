package com.waiter.server.api.venue;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.venue.model.VenueModel;
import com.waiter.server.api.venue.model.request.AddVenueRequest;
import com.waiter.server.services.user.model.User;
import com.waiter.server.services.company.CompanyService;
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
public class VenueController extends AuthenticationController {

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

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<VenueModel> create(@RequestBody AddVenueRequest addVenueRequest, @ModelAttribute User user) {
        VenueDto venueDto = new VenueDto();
        venueDto.setMenuId(addVenueRequest.getMenuId());
        venueDto.setCompanyId(user.getCompany().getId());
        venueDto.setName(addVenueRequest.getName());
        venueDto.setLocation(LocationModel.convert(addVenueRequest.getLocation()));

        Venue createdVenue = venueService.create(venueDto);
        VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<VenueModel> update(@RequestParam Long id, @RequestBody AddVenueRequest addVenueRequest) {

        //todo check user permission
        VenueDto venueDto = new VenueDto();
        venueDto.setMenuId(addVenueRequest.getMenuId());
        venueDto.setLocation(LocationModel.convert(addVenueRequest.getLocation()));
        venueDto.setName(addVenueRequest.getName());

        Venue createdVenue = venueService.updateVenue(id, venueDto);
        VenueModel venueModel = VenueModel.convert(createdVenue);
        return ResponseEntity.success(venueModel);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> findVenues(@RequestBody VenueSearchParameters parameters) {
        List<Venue> venues = venueService.getVenuesBySearchParameters(parameters);
        return ResponseEntity.success(venues);
    }

}
