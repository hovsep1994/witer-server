package com.waiter.server.services.venue.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.model.Menu;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@Entity
@Table(name = "venue")
public class Venue extends AbstractEntityModel {

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<VenueType> venueTypes;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = true)
    private Menu menu;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;

    @Column(name = "source")
    private String source;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "venue")
    private List<Schedule> openHours;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Set<VenueType> getVenueTypes() {
        return venueTypes;
    }

    public void setVenueTypes(Set<VenueType> venueTypes) {
        this.venueTypes = venueTypes;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public Venue setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Venue setSource(String source) {
        this.source = source;
        return this;
    }

    public List<Schedule> getOpenHours() {
        if (openHours == null) {
            openHours = new ArrayList<>();
        }
        return openHours;
    }

    public void setOpenHours(List<Schedule> openHours) {
        this.openHours = openHours;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("location", location)
                .append("company", company)
                .append("menu", menu)
                .append("gallery", gallery)
                .append("venueTypes", venueTypes)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, venue.name)
                .append(location, venue.location)
                .append(company, venue.company)
                .append(menu, venue.menu)
                .append(gallery, venue.gallery)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(location)
                .append(company)
                .append(menu)
                .append(gallery)
                .toHashCode();
    }
}
