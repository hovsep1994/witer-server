package com.waiter.server.solr.core.repository.product.model;

import com.waiter.server.services.language.Language;
import com.waiter.server.solr.core.repository.category.CategorySolrDocument;
import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
@SolrDocument(solrCoreName = "product")
public class ProductDocument extends AbstractSolrDocumentWithId {

    private static final long serialVersionUID = -1801268777977380163L;

    @Field("name")
    private String name;

    @Field("language")
    private String language;

    @Field("tags")
    private List<String> tags;

    @Field("description")
    private String description;

    @Field("galleryId")
    private String galleryId;

    @Field("evaluation")
    private Double evaluation;

    @Field("price")
    private Double price;

    @Field("menuId")
    private String menuId;

    @Field(value = "category", child = true)
    private CategorySolrDocument categorySolrDocument;

    @Field(value = "venue", child = true)
    private VenueSolrDocument venueSolrDocument;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public CategorySolrDocument getCategorySolrDocument() {
        return categorySolrDocument;
    }

    public void setCategorySolrDocument(CategorySolrDocument categorySolrDocument) {
        this.categorySolrDocument = categorySolrDocument;
    }

    public VenueSolrDocument getVenueSolrDocument() {
        return venueSolrDocument;
    }

    public void setVenueSolrDocument(VenueSolrDocument venueSolrDocument) {
        this.venueSolrDocument = venueSolrDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProductDocument that = (ProductDocument) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, that.name)
                .append(language, that.language)
                .append(tags, that.tags)
                .append(description, that.description)
                .append(galleryId, that.galleryId)
                .append(evaluation, that.evaluation)
                .append(price, that.price)
                .append(menuId, that.menuId)
                .append(categorySolrDocument, that.categorySolrDocument)
                .append(venueSolrDocument, that.venueSolrDocument)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(language)
                .append(tags)
                .append(description)
                .append(galleryId)
                .append(evaluation)
                .append(price)
                .append(menuId)
                .append(categorySolrDocument)
                .append(venueSolrDocument)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("language", language)
                .append("tags", tags)
                .append("description", description)
                .append("galleryId", galleryId)
                .append("evaluation", evaluation)
                .append("price", price)
                .append("menuId", menuId)
                .append("categorySolrDocument", categorySolrDocument)
                .append("venueSolrDocument", venueSolrDocument)
                .toString();
    }
}
