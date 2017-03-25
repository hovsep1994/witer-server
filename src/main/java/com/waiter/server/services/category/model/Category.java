package com.waiter.server.services.category.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "category")
public class Category extends AbstractEntityModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> products;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "category_name")
    private Set<Translation> nameSet;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;

    public Category() {
        nameSet = new HashSet<>();
        tags = new HashSet<>();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Translation> getNameSet() {
        return nameSet;
    }

    public void setNameSet(Set<Translation> nameSet) {
        this.nameSet = nameSet;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Translation getNameTranslationByLanguage(Language language) {
        return nameSet.stream().filter(nameTranslation ->
                nameTranslation.getLanguage().equals(language)).findFirst().orElse(null);
    }

    public Set<Language> getLanguages() {
        return nameSet.stream().map(Translation::getLanguage).collect(Collectors.toSet());
    }
}
