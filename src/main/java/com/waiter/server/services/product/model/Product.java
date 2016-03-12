package com.waiter.server.services.product.model;

import com.waiter.server.services.common.model.AbstractNamedEntityModel;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.tag.model.Tag;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "product")
public class Product extends AbstractNamedEntityModel {

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    public Product() {
        tags = new HashSet<>();
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
