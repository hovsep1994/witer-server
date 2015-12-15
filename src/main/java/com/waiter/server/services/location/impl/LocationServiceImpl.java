package com.waiter.server.services.location.impl;

import com.waiter.server.commons.entities.City;
import com.waiter.server.commons.entities.Country;
import com.waiter.server.repository.sql.LocationsRepository;
import com.waiter.server.services.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationsRepository locationsRepository;

    @Override
    public List<Country> getAllCountries() {
        return locationsRepository.getAllCountries();
    }

    @Override
    public List<City> searchCities(String nameQuery, String countryCode, int limit) {
        return locationsRepository.searchCities(nameQuery, countryCode, limit);
    }
}
