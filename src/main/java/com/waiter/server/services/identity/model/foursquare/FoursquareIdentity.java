package com.waiter.server.services.identity.model.foursquare;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by hovsep on 5/14/16.
 */
@Entity
@Table(name = "venueFoursquareIdentity")
public class FoursquareIdentity extends AbstractEntityModel {

    @Column(name = "venue_id", nullable = false)
    private Long venueId;

    @Column(name = "referralId", nullable = false)
    private String referralId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "json", nullable = false)
    private String json;

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
