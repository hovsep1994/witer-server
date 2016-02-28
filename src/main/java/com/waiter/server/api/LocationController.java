package com.waiter.server.api;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.model.City;
import com.waiter.server.services.location.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Admin on 1/17/2016.
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/countries/all", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> searchCountries() {
        List<Country> countries = locationService.getAllCountries();
        return ResponseEntity.forResponse(countries);
    }

    @RequestMapping(value = "/cities/search", method = RequestMethod.GET)
    public ResponseEntity<List<City>> searchCities(String name, String countryCode, int from, int to) {
        List<City> cities = locationService.searchCities(name, countryCode, from, to);
        return ResponseEntity.forResponse(cities);
    }

}
