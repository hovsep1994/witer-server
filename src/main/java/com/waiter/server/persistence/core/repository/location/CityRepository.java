package com.waiter.server.persistence.core.repository.location;

import com.waiter.server.services.location.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameCountryCode(String name, String countryCode, Pageable pageable);
}
