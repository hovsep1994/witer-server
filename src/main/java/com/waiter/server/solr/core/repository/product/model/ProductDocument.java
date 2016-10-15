package com.waiter.server.solr.core.repository.product.model;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;
import java.util.Set;

/**
 * Created by hovsep on 7/31/16.
 */
@SolrDocument(solrCoreName = "product")
public class ProductDocument extends AbstractSolrDocumentWithId {

    private static final long serialVersionUID = -1801268777977380163L;

    @Field("productNameTranslations")
    private List<String> productNameTranslations;

    @Field("productTags")
    private Set<String> productTags;

    @Field("descriptionTranslations")
    private List<String> descriptionTranslations;

    @Field("galleryId")
    private Long galleryId;

    @Field("evaluation")
    private Double evaluation;

    @Field("price")
    private Double price;

    @Field("menuId")
    private Long menuId;

    @Field("categoryId")
    private Long categoryId;

    @Field("categoryNameTranslations")
    private List<String> categoryNameTranslations;

    @Field("categoryTags")
    private Set<String> categoryTags;

    @Field("venueId")
    private Long venueId;

    @Field("venueName")
    private String venueName;

    @Field("location")
    private Set<SolrLocation> locations;

    @Field("companyId")
    private Long companyId;

    public ProductDocument() {
//        productNameTranslations = new ArrayList<>();
//        productTags = new HashSet<>();
//        categoryNameTranslations = new ArrayList<>();
//        categoryTags = new HashSet<>();
//        descriptionTranslations = new ArrayList<>();
//        venueName = "";
    }

    public List<String> getProductNameTranslations() {
        return productNameTranslations;
    }

    public void setProductNameTranslations(List<String> productNameTranslations) {
        this.productNameTranslations = productNameTranslations;
    }

    public Set<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(Set<String> productTags) {
        this.productTags = productTags;
    }

    public List<String> getDescriptionTranslations() {
        return descriptionTranslations;
    }

    public void setDescriptionTranslations(List<String> descriptionTranslations) {
        this.descriptionTranslations = descriptionTranslations;
    }

    public Long getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Long galleryId) {
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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getCategoryNameTranslations() {
        return categoryNameTranslations;
    }

    public void setCategoryNameTranslations(List<String> categoryNameTranslations) {
        this.categoryNameTranslations = categoryNameTranslations;
    }

    public Set<String> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(Set<String> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Set<SolrLocation> getLocations() {
        return locations;
    }

    public void setLocations(Set<SolrLocation> locations) {
        this.locations = locations;
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

        ProductDocument that = (ProductDocument) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(productNameTranslations, that.productNameTranslations)
                .append(productTags, that.productTags)
                .append(descriptionTranslations, that.descriptionTranslations)
                .append(galleryId, that.galleryId)
                .append(evaluation, that.evaluation)
                .append(price, that.price)
                .append(menuId, that.menuId)
                .append(categoryId, that.categoryId)
                .append(categoryNameTranslations, that.categoryNameTranslations)
                .append(categoryTags, that.categoryTags)
                .append(venueId, that.venueId)
                .append(venueName, that.venueName)
                .append(locations, that.locations)
                .append(companyId, that.companyId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(productNameTranslations)
                .append(productTags)
                .append(descriptionTranslations)
                .append(galleryId)
                .append(evaluation)
                .append(price)
                .append(menuId)
                .append(categoryId)
                .append(categoryNameTranslations)
                .append(categoryTags)
                .append(venueId)
                .append(venueName)
                .append(locations)
                .append(companyId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("productNameTranslations", productNameTranslations)
                .append("productTags", productTags)
                .append("descriptionTranslations", descriptionTranslations)
                .append("galleryId", galleryId)
                .append("evaluation", evaluation)
                .append("price", price)
                .append("menuId", menuId)
                .append("categoryId", categoryId)
                .append("categoryNameTranslations", categoryNameTranslations)
                .append("categoryTags", categoryTags)
                .append("venueId", venueId)
                .append("venueName", venueName)
                .append("locations", locations)
                .append("companyId", companyId)
                .toString();
    }
}
