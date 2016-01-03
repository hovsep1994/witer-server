package com.waiter.server.persistence.core.repository.name;

import com.waiter.server.services.name.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface NameRepository extends JpaRepository<Name, Long> {

    List<Name> insertNames(List<Name> names);

}
