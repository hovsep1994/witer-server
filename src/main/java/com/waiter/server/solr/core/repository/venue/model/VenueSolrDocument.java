package com.waiter.server.solr.core.repository.venue.model;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.geo.Point;

/**
 * Created by hovsep on 7/31/16.
 */
public class VenueSolrDocument extends AbstractSolrDocumentWithId {
    private static final long serialVersionUID = -1941047069037577163L;

    @Field("venueId")
    private Long venueId;

    @Field("name")
    private String name;

    @Field("location")
    private Point location;

    @Field("companyId")
    private Long companyId;

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VenueSolrDocument that = (VenueSolrDocument) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(venueId, that.venueId)
                .append(name, that.name)
                .append(location, that.location)
                .append(companyId, that.companyId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(venueId)
                .append(name)
                .append(location)
                .append(companyId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("venueId", venueId)
                .append("name", name)
                .append("location", location)
                .append("companyId", companyId)
                .toString();
    }
}
