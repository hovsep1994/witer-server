package com.waiter.server.services.location.impl;

import com.waiter.server.persistence.core.repository.location.CityRepository;
import com.waiter.server.persistence.core.repository.location.CountryRepository;
import com.waiter.server.persistence.core.repository.location.LocationRepository;
import com.waiter.server.services.common.exception.Assert;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.location.dto.LocationDto;
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
    public Location getById(Long id) {
        notNull(id);
        Location location = locationRepository.findOne(id);
        if (location == null) {
            LOGGER.debug("Location with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Location not found");
        }
        return location;
    }

    @Override
    public Location create(LocationDto locationDto) {
        notNull(locationDto);
        notNull(locationDto.getCity());
        Location location = new Location();
        locationDto.updateProperties(location);
        return locationRepository.save(location);
    }

    @Override
    public Location update(Long id, LocationDto locationDto) {
        notNull(id);
        notNull(locationDto);
        final Location location = getById(id);
        locationDto.updateProperties(location);
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
