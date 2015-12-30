package com.waiter.server.persistence.core.repository.tag;

import com.waiter.server.services.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    //TODO what is this??
    void batchInsert(List<Tag> tags);

    List<Integer> insertAndGetIds(List<Tag> tags);

    List<Integer> findTagIds(List<String> tags);

}
