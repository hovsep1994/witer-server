package com.waiter.server.services.tag;

import com.waiter.server.services.tag.model.Tag;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface TagService {

    List<Long> insertAndGetIds(List<Tag> tags);

    List<Tag> findTagIds(List<String> tags);
}
