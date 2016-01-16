package com.waiter.server.services.group.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.name.model.Name;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "group")
public class Group extends AbstractEntityModel {

    @Column(name = "image")
    private String image;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Name> names;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (menu != null ? !menu.equals(group.menu) : group.menu != null) return false;
        if (image != null ? !image.equals(group.image) : group.image != null) return false;
        if (names != null ? !names.equals(group.names) : group.names != null) return false;
        if (products != null ? !products.equals(group.products) : group.products != null) return false;
        return !(tags != null ? !tags.equals(group.tags) : group.tags != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (names != null ? names.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "menu=" + menu +
                ", image='" + image + '\'' +
                ", names=" + names +
                ", products=" + products +
                ", tags=" + tags +
                "} " + super.toString();
    }
}
