package com.waiter.server.persistence.core.repository.group;

import com.waiter.server.services.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}