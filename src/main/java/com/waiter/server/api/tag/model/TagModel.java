package com.waiter.server.api.tag.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waiter.server.api.common.model.AbstractApiModel;
import com.waiter.server.services.tag.model.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:31 PM
 */
public class TagModel extends AbstractApiModel {

    @JsonProperty(value = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Tag convert(TagModel tagModel) {
        Tag tag = new Tag();
        tag.setName(tagModel.getName());
        return tag;
    }

    public static TagModel convert(Tag tag) {
        TagModel tagModel = new TagModel();
        tagModel.setName(tag.getName());
        return tagModel;
    }

    public static Set<Tag> convert(Set<TagModel> tagModels) {
        if (tagModels == null) {
            return null;
        }
        Set<Tag> tags = new HashSet<>(tagModels.size());
        tagModels.stream().forEach(tagModel -> {
            tags.add(convert(tagModel));
        });
        return tags;
    }

    public static Set<Tag> convertStringsToTags(Set<String> tagStrings) {
        if (tagStrings == null) {
            return null;
        }
        return tagStrings.stream().map(Tag::new).collect(Collectors.toSet());
    }

    public static Set<String> convertTagsToStrings(Set<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }

    public static Set<TagModel> convertToModel(Set<Tag> tags) {
        if (tags == null) {
            return null;
        }
        final Set<TagModel> tagModels = new HashSet<>(tags.size());
        tags.forEach(tag -> tagModels.add(convert(tag)));
        return tagModels;
    }

}
