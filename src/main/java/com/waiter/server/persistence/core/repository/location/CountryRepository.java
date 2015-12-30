package com.waiter.server.persistence.core.repository.location;

import com.waiter.server.services.location.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
