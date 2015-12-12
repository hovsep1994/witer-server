package com.waiter.server.commons.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Group {

    private int id;
    private List<Name> names = new ArrayList<>();
    private String image;
    private List<Product> products;
    private List<Tag> tags;
    private Menu menu;

    public int getId() {
        return id;
    }

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Name getName() {
        if(names == null || names.size() == 0) {
            return null;
        }
        return names.get(0);
    }

    public Group setName(Name name) {
        names = new ArrayList<>();
        names.add(name);
        return this;
    }

    public Group addName(Name name) {
        names.add(name);
        return this;
    }

    public String getImage() {
        return image;
    }

    public Group setImage(String image) {
        this.image = image;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Group setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Group setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public Menu getMenu() {
        return menu;
    }

    public Group setMenu(Menu menu) {
        this.menu = menu;
        return this;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", products=" + products +
                ", tags=" + tags +
                ", menu=" + menu +
                '}';
    }
}
