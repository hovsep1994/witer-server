package com.waiter.server.services.location;

import com.waiter.server.commons.entities.City;
import com.waiter.server.commons.entities.Country;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
public interface LocationService {

    List<Country> getAllCountries();
    List<City> searchCities(String nameQuery, String countryCode, int limit);
}
