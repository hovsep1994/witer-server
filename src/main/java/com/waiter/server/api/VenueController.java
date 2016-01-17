package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(venueService.get(id));
    }

    @RequestMapping(value = "/add}", method = RequestMethod.POST)
    public ResponseEntity<Venue> createMenu(@RequestBody Venue venue) {
        Venue createdVenue = venueService.create(venue);
        return new ResponseEntity<>(createdVenue);
    }
}
