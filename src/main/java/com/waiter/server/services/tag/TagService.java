package com.waiter.server.services.tag;

import com.waiter.server.services.tag.model.Tag;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface TagService {

    void batchInsert(List<Tag> tags);

    List<Integer> insertAndGetIds(List<Tag> tags);

    List<Integer> findTagIds(List<String> tags);
}
