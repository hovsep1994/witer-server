package com.waiter.server.api.venue;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.api.venue.model.request.AddVenueRequest;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.venue.VenueService;
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
public class VenueController {

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Venue> findOne(@PathVariable Long id) {
        return ResponseEntity.forResponse(venueService.getVenueById(id));
    }

    @RequestMapping(value = "/add}", method = RequestMethod.POST)
    public ResponseEntity<Venue> createMenu(@RequestBody AddVenueRequest addVenueRequest) {
        Venue venue = new Venue();
        Company company = new Company();
        company.setId(addVenueRequest.getCompanyId());
        venue.setCompany(company);
        Location location = new Location();
        location.setCity(addVenueRequest.getLocation().getCity());
        venue.setLocation(location);
        Menu menu = new Menu();
        menu.setId(addVenueRequest.getMenuId());
        venue.setMenu(menu);
        Venue createdVenue = venueService.create(venue);
        return ResponseEntity.forResponse(createdVenue);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> findVenues(@RequestBody VenueSearchParameters parameters) {
        List<Venue> venues = venueService.getVenuesBySearchParameters(parameters);
        return ResponseEntity.forResponse(venues);
    }

}
