package com.waiter.server.services.tag.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * Created by Admin on 12/24/2015.
 */
@Entity
@Table(name = "tag")
public class Tag extends AbstractEntityModel {

    @Column(name = "name", nullable = false)
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tag tag = (Tag) o;

        return name != null ? name.equals(tag.name) : tag.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public static List<String> toStrings(List<Tag> tags) {
        List<String> list = new ArrayList<>(tags.size());
        for (Tag tag : tags) {
            list.add(tag.getName());
        }
        return list;
    }

    public static Set<Tag> parseTags(Set<String> tags) {
        if (tags == null) return null;
        Set<Tag> tagList = new HashSet<>(tags.size());
        for (String t : tags) {
            tagList.add(new Tag(t));
        }
        return tagList;
    }

    public static Set<Tag> parseTags(String tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return parseTags(new HashSet<String>(Arrays.asList(tags.split(","))));
    }

}
