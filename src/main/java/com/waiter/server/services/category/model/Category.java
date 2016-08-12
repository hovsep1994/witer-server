package com.waiter.server.services.category.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import javax.persistence.*;
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

    @Column(name = "image")
    private String image;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Translation> translations;

    public Category() {
        translations = new HashSet<>();
        tags = new HashSet<>();
    }

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

    public List<Product> getProducts() {
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

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }

    public Translation getNameTranslationByLanguage(Language language) {
        return translations.stream().filter(nameTranslation ->
                nameTranslation.getLanguage().equals(language)).findFirst().orElse(null);
    }

    public Set<Language> getLanguages() {
        return translations.stream().map(Translation::getLanguage).collect(Collectors.toSet());
    }
}
