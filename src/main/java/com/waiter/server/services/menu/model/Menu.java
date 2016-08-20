package com.waiter.server.services.menu.model;

import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.language.Language;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "menu")
public class Menu extends AbstractEntityModel {

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<Category> categories;

    @ElementCollection(targetClass = Language.class)
    @CollectionTable(name = "menu_language")
    @Column(name = "language")
    private Set<Language> languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, menu.name)
                .append(company, menu.company)
                .append(categories, menu.categories)
                .append(languages, menu.languages)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(company)
                .append(categories)
                .append(languages)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("company", company)
                .append("categories", categories)
                .append("languages", languages)
                .toString();
    }
}
