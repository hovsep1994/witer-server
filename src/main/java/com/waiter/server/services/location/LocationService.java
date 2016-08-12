package com.waiter.server.services.location;

import com.waiter.server.services.location.model.City;
import com.waiter.server.services.location.model.Country;
import com.waiter.server.services.location.model.Location;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
public interface LocationService {

    Location getLocationById(Long id);

    Location createLocation(Location location);

    Location updateLocation(Location location);

    List<Country> getAllCountries();

    List<City> searchCities(String name, String countryCode, int form, int to);
}
