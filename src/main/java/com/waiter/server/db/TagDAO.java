package com.waiter.server.db;

import com.waiter.server.commons.entities.Tag;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface TagDAO {
    void batchInsert(List<Tag> tags);
    List<Integer> insertAndGetIds(List<Tag> tags);
    List<Integer> findTagIds(List<String> tags);
}
