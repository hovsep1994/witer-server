package com.waiter.server.services.tag.model;

import com.waiter.server.services.common.model.AbstractNamedEntityModel;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 12/24/2015.
 */
@Entity
@Table(name = "tag")
public class Tag extends AbstractNamedEntityModel {

    public Tag() {
    }

    public Tag(String name) {
        setName(name);
    }

    public static List<String> toStrings(List<Tag> tags) {
        List<String> list = new ArrayList<>(tags.size());
        for (Tag tag : tags) {
            list.add(tag.getName());
        }
        return list;
    }

    public static List<Tag> parseTags(List<String> tags) {
        if (tags == null) return null;
        List<Tag> tagList = new ArrayList<>(tags.size());
        for (String t : tags) {
            tagList.add(new Tag(t));
        }
        return tagList;
    }

    public static List<Tag> parseTags(String tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        return parseTags(Arrays.asList(tags.split(",")));
    }

    public static String[] getNamesArray(List<Tag> tags) {
        String strings[] = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            strings[i] = tags.get(i).getName();
        }
        return strings;
    }

}
