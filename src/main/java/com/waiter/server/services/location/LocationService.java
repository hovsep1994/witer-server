package com.waiter.server.services.location;

import com.waiter.server.services.location.dto.LocationDto;
import com.waiter.server.services.location.model.City;
import com.waiter.server.services.location.model.Country;
import com.waiter.server.services.location.model.Location;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
public interface LocationService {

    Location getById(Long id);

    Location create(LocationDto locationDto);

    Location update(Long id, LocationDto locationDto);

    List<Country> getAllCountries();

    List<City> searchCities(String name, String countryCode, int form, int to);
}
