package com.waiter.server.services.location.impl;

import com.waiter.server.persistence.core.repository.location.CityRepository;
import com.waiter.server.persistence.core.repository.location.CountryRepository;
import com.waiter.server.persistence.core.repository.location.LocationRepository;
import com.waiter.server.services.common.exception.Assert;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.model.City;
import com.waiter.server.services.location.model.Country;
import com.waiter.server.services.location.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Admin on 12/15/2015.
 */
@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location getLocationById(Long id) {
        notNull(id);
        Location location = locationRepository.findOne(id);
        if (location == null) {
            LOGGER.debug("Location with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Location not found");
        }
        return location;
    }

    @Override
    public Location createLocation(Location location) {
        notNull(location);
        notNull(location.getCity());
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Location location) {
        notNull(location);
        notNull(location.getId());
        return locationRepository.save(location);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<City> searchCities(String name, String countryCode, int from, int to) {
        Pageable pageRequest = new PageRequest(from, to);
//        List<City> cities = cityRepository.findByNameAndCountryCode(name, countryCode);
        return null;
    }
}
