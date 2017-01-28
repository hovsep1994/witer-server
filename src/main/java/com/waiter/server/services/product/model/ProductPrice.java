package com.waiter.server.services.product.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.model.Translation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hovsep on 9/10/16.
 */
@Entity
@Table(name = "product_price")
public class ProductPrice extends AbstractEntityModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_price_name")
    private Set<Translation> names;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Translation> getNames() {
        if (names == null) {
            names = new HashSet<>();
        }
        return names;
    }

    public void setNames(Set<Translation> names) {
        this.names = names;
    }

    public Translation getNameTranslationByLanguage(Language language) {
        return Translation.getTranslationByLanguage(getNames(), language);
    }

    public String getNameByLangauge(Language language) {
        return Translation.getTranslationTextByLanguage(getNames(), language);
    }
}
