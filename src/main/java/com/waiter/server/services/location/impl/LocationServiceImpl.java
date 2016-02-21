package com.waiter.server.services.location.impl;

import com.waiter.server.persistence.core.repository.location.CityRepository;
import com.waiter.server.persistence.core.repository.location.CountryRepository;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.model.City;
import com.waiter.server.services.location.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<City> searchCities(String name, String countryCode, int from, int to) {
        Pageable pageRequest = new PageRequest(from, to);
        List<City> cities = cityRepository.findByNameAndCountryCode(name, countryCode);
        return cities;
    }
}
