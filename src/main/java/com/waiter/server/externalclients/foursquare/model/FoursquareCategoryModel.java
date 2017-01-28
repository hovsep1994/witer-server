package com.waiter.server.externalclients.foursquare.model;

/**
 * Created by hovsep on 5/15/16.
 */
public class FoursquareCategoryModel {

    private String id;
    private String name;
    private String pluralName;
    private String shortName;
    private Boolean primary;
    private FoursquareIconModel icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPluralName() {
        return pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public FoursquareIconModel getIcon() {
        return icon;
    }

    public void setIcon(FoursquareIconModel icon) {
        this.icon = icon;
    }
}
