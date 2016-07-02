package com.waiter.server.externalclients.foursquare.model;

/**
 * Created by Admin on 2/2/2016.
 */
public class FoursquareModel {

    private String referralId;
    private FoursquareVenueModel venue;
    private String json;

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public FoursquareVenueModel getVenue() {
        return venue;
    }

    public void setVenue(FoursquareVenueModel venue) {
        this.venue = venue;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
