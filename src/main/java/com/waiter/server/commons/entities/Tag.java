package com.waiter.server.commons.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Tag {
    private int id;
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Tag setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public static List<String> toStrings(List<Tag> tags) {
        List<String> list = new ArrayList<>(tags.size());
        for(Tag tag : tags) {
            list.add(tag.getName());
        }
        return list;
    }

    public static List<Tag> parseTags(List<String> tags) {
        if(tags == null) return null;
        List<Tag> tagList = new ArrayList<>(tags.size());
        for (String t : tags) {
            tagList.add(new Tag(t));
        }
        return tagList;
    }

    public static List<Tag> parseTags(String tags) {
        if(tags == null) {
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

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
