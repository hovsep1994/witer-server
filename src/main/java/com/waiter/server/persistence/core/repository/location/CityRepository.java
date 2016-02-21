package com.waiter.server.persistence.core.repository.location;

import com.waiter.server.services.location.model.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT ci, co FROM City AS ci " +
            "LEFT JOIN Country AS co ON ci.country_id = co.id " +
            "WHERE ci.name like :name and co.code like :countryCode")
    List<City> findByNameAndCountryCode(@Param("name") String name, @Param("countryCode") String countryCode);

}
