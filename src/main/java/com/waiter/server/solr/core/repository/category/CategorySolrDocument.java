package com.waiter.server.solr.core.repository.category;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocument;
import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
public class CategorySolrDocument extends AbstractSolrDocument {
    private static final long serialVersionUID = -6596387152588059874L;

    @Field("categoryId")
    private Long categoryId;

    @Field("name")
    private String name;

    @Field("categoryTags")
    private List<String> tags;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CategorySolrDocument that = (CategorySolrDocument) o;

        return new EqualsBuilder()
                .append(categoryId, that.categoryId)
                .append(name, that.name)
                .append(tags, that.tags)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(categoryId)
                .append(name)
                .append(tags)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("categoryId", categoryId)
                .append("name", name)
                .append("tags", tags)
                .toString();
    }
}
