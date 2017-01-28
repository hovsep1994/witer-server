package com.waiter.server.services.tag;

import com.waiter.server.services.tag.model.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author shahenpoghosyan
 */
public interface TagService {

    Set<Tag> create(Set<String> tagNames);

}
