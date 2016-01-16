package com.waiter.server.services.tag.impl;

import com.waiter.server.persistence.core.repository.tag.TagRepository;
import com.waiter.server.services.tag.TagService;
import com.waiter.server.services.tag.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Long> insertAndGetIds(List<Tag> tags) {
        Assert.notNull(tags, "Tags must not be null");
        List<Tag> savedTags = tagRepository.save(tags);
        List<Long> ids = new ArrayList<>(savedTags.size());
        for (int i = 0; i < savedTags.size(); i++) {
            ids.add(i, savedTags.get(i).getId());
        }
        return ids;
    }

    @Override
    public List<Tag> findTagIds(List<String> tags) {
        List<Tag> savedTags = tagRepository.findByNames(tags);
        return savedTags;
    }
}

