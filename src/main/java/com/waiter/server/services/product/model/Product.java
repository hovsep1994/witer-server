package com.waiter.server.services.product.model;

import com.waiter.server.services.common.model.AbstractNamedEntityModel;
import com.waiter.server.services.group.model.Group;
import com.waiter.server.services.name.model.NameTranslation;
import com.waiter.server.services.tag.model.Tag;

import javax.persistence.*;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "product")
public class Product extends AbstractNamedEntityModel {

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NameTranslation> nameTranslations;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Tag> tags;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<NameTranslation> getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(List<NameTranslation> nameTranslations) {
        this.nameTranslations = nameTranslations;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
