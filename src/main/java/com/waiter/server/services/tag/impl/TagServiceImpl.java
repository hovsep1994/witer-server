package com.waiter.server.services.tag.impl;

import com.waiter.server.persistence.core.repository.tag.TagRepository;
import com.waiter.server.services.tag.TagService;
import com.waiter.server.services.tag.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    public Set<Tag> create(Set<String> tagNames) {
        final Set<Tag> existingTags = tagRepository.findByNames(tagNames);
        tagNames.removeAll(Tag.toStringList(existingTags));
        final Set<Tag> tags = tagNames.stream().map(Tag::new).collect(Collectors.toSet());
        final List<Tag> createdTags = tagRepository.save(tags);
        existingTags.addAll(createdTags);
        return existingTags;
    }

}

