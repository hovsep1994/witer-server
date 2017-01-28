package com.waiter.server.persistence.core.repository.tag;

import com.waiter.server.services.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "SELECT t FROM Tag t WHERE t.name IN (:names)")
    Set<Tag> findByNames(@Param("names") Collection<String> names);

}
