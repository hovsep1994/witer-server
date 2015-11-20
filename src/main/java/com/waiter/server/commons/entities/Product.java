package com.waiter.server.commons.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Product {

    private int id;
    private String image;
    private String description;
    private double price;
    private List<Name> names = new ArrayList<>();
    private Group group;
    private List<Tag> tags;

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public List<Name> getNames() {
        return names;
    }

    public Product setNames(List<Name> names) {
        this.names = names;
        return this;
    }

    public Product setName(Name name) {
        names = new ArrayList<>();
        names.add(name);
        return this;
    }

    public Product addName(Name name) {
        names.add(name);
        return this;
    }

    public Product setImage(String image) {
        this.image = image;
        return this;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public Product setGroup(Group group) {
        this.group = group;
        return this;
    }

    public Product setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }


    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Group getGroup() {
        return group;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", group=" + group +
                ", tags=" + tags +
                '}';
    }
}
