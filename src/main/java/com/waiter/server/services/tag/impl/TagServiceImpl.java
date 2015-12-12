package com.waiter.server.services.tag.impl;

import com.waiter.server.commons.entities.Tag;
import com.waiter.server.repository.sql.TagRepository;
import com.waiter.server.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void batchInsert(List<Tag> tags) {
        tagRepository.batchInsert(tags);
    }

    @Override
    public List<Integer> insertAndGetIds(List<Tag> tags) {
        return tagRepository.insertAndGetIds(tags);
    }

    @Override
    public List<Integer> findTagIds(List<String> tags) {
        return tagRepository.findTagIds(tags);
    }
}
