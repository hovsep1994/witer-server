package com.waiter.server.services.product.model;

import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translation.model.Translation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "product")
public class Product extends AbstractEntityModel {

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ProductPrice> productPrices;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_description")
    private Set<Translation> descriptionSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_name")
    private Set<Translation> nameSet;

    @Column(name = "isAvailable")
    private Boolean isAvailable = true;

    public Product() {
        descriptionSet = new HashSet<>();
        nameSet = new HashSet<>();
        tags = new HashSet<>();
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Set<ProductPrice> getProductPrices() {
        if(productPrices == null){
            productPrices = new HashSet<>();
        }
        return productPrices;
    }

    public void setProductPrices(Set<ProductPrice> productPrices) {
        this.productPrices = productPrices;
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

    public Set<Translation> getDescriptionSet() {
        return descriptionSet;
    }

    public void setDescriptionSet(Set<Translation> descriptionSet) {
        this.descriptionSet = descriptionSet;
    }

    public Set<Translation> getNameSet() {
        return nameSet;
    }

    public void setNameSet(Set<Translation> nameSet) {
        this.nameSet = nameSet;
    }

    public Double getAverageRating() {
        return evaluation.getAverageRating();
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Translation getNameTranslationByLanguage(Language language) {
        return Translation.getTranslationByLanguage(getNameSet(), language);
    }

    public Translation getDescriptionByLanguage(Language language) {
        return Translation.getTranslationByLanguage(getDescriptionSet(), language);
    }

    public String getDescriptionTextByLanguage(Language language) {
        return Translation.getTranslationTextByLanguage(getDescriptionSet(), language);
    }
}
