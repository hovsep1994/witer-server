package com.waiter.server.commons.entities;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class Group {

    private int id;
    private String name;
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

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", products=" + products +
                ", tags=" + tags +
                ", menu=" + menu +
                '}';
    }
}
