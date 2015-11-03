package com.waiter.server.db;

import com.waiter.server.commons.entities.City;
import com.waiter.server.commons.entities.Country;

import java.util.List;

public interface LocationsDAO {

    List<Country> getAllCountries();
    List<City> searchCities(String nameQuery, String countryCode, int limit);
}
