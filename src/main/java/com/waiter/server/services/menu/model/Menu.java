package com.waiter.server.services.menu.model;

import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.venue.model.Venue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private List<Venue> venues;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<Category> categories;

    @ElementCollection(targetClass = Language.class)
    @CollectionTable(name = "menu_language")
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Set<Language> languages;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_language")
    private Language mainLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

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
        if (languages == null) {
            languages = new HashSet<>();
        }
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Language getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(Language mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
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
                .append(venues, menu.venues)
                .append(categories, menu.categories)
                .append(languages, menu.languages)
                .append(mainLanguage, menu.mainLanguage)
                .append(currency, menu.currency)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(company)
                .append(venues)
                .append(categories)
                .append(languages)
                .append(mainLanguage)
                .append(currency)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("company", company)
                .append("venues", venues)
                .append("categories", categories)
                .append("languages", languages)
                .append("mainLanguage", mainLanguage)
                .append("currency", currency)
                .toString();
    }
}
