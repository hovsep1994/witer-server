package com.waiter.server.solr.core.repository.product.model;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
@SolrDocument(solrCoreName = "product")
public class ProductDocument extends AbstractSolrDocumentWithId {

    private static final long serialVersionUID = -1801268777977380163L;

    @Field("productId")
    private Long productId;

    @Field("productName")
    private String productName;

    @Field("language")
    private String language;

    @Field("productTags")
    private List<String> productTags;

    @Field("description")
    private String description;

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

    @Field("categoryName")
    private String categoryName;

    @Field("categoryTags")
    private List<String> categoryTags;

    @Field("venueId")
    private Long venueId;

    @Field("name")
    private String venueName;

    @Field("location")
    private Point location;

    @Field("companyId")
    private Long companyId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(List<String> categoryTags) {
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

        ProductDocument that = (ProductDocument) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(productId, that.productId)
                .append(productName, that.productName)
                .append(language, that.language)
                .append(productTags, that.productTags)
                .append(description, that.description)
                .append(galleryId, that.galleryId)
                .append(evaluation, that.evaluation)
                .append(price, that.price)
                .append(menuId, that.menuId)
                .append(categoryId, that.categoryId)
                .append(categoryName, that.categoryName)
                .append(categoryTags, that.categoryTags)
                .append(venueId, that.venueId)
                .append(venueName, that.venueName)
                .append(location, that.location)
                .append(companyId, that.companyId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(productId)
                .append(productName)
                .append(language)
                .append(productTags)
                .append(description)
                .append(galleryId)
                .append(evaluation)
                .append(price)
                .append(menuId)
                .append(categoryId)
                .append(categoryName)
                .append(categoryTags)
                .append(venueId)
                .append(venueName)
                .append(location)
                .append(companyId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("productId", productId)
                .append("productName", productName)
                .append("language", language)
                .append("productTags", productTags)
                .append("description", description)
                .append("galleryId", galleryId)
                .append("evaluation", evaluation)
                .append("price", price)
                .append("menuId", menuId)
                .append("categoryId", categoryId)
                .append("categoryName", categoryName)
                .append("categoryTags", categoryTags)
                .append("venueId", venueId)
                .append("venueName", venueName)
                .append("location", location)
                .append("companyId", companyId)
                .toString();
    }
}
