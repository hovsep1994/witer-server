package com.waiter.server.solr.core.repository.venue.model;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by hovsep on 7/31/16.
 */
@SolrDocument(solrCoreName = "venue")
public class VenueSolrDocument extends AbstractSolrDocumentWithId {

    private static final long serialVersionUID = -1941047069037577163L;

    @Field("name")
    @Indexed
    private String name;

    @Field("location")
    @Dynamic
    @Indexed(type = "solr.PointType", stored = false)
    private Point location;

    @Field("companyId")
    private Long companyId;

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
                .append(name, that.name)
                .append(location, that.location)
                .append(companyId, that.companyId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(location)
                .append(companyId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("location", location)
                .append("companyId", companyId)
                .toString();
    }
}
