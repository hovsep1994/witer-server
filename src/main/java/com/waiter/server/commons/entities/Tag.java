package com.waiter.server.commons.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Tag {
    private int id;
    private String name;

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
}
