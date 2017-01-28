package com.waiter.server.solr.core.repository.product.model;

import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.venue.model.Venue;
import com.waiter.server.solr.core.repository.common.model.SolrLocation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
public class ProductInputDocument {

    private String id;
    private List<String> categoryTags;
    private List<String> productTags;
    private List<SolrLocation> locations;
    private List<String> names;
    private List<String> descriptions;
    private Long menuId;
    private double rating;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(List<String> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public List<SolrLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<SolrLocation> locations) {
        this.locations = locations;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public static ProductInputDocument convert(Product product) {
        ProductInputDocument document = new ProductInputDocument();
        List<SolrLocation> locations = product.getCategory().getMenu().getVenues().stream()
                .map(Venue::getLocation)
                .map(x -> new SolrLocation(x.getLatitude(), x.getLongitude()))
                .collect(Collectors.toList());
        document.setId(product.getId().toString());
        document.setLocations(locations);
        document.setCategoryTags(product.getCategory().getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        document.setProductTags(product.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        document.setNames(Translation.getListOfTexts(product.getNameSet()));
        document.setDescriptions(Translation.getListOfTexts(product.getDescriptionSet()));
        document.setRating(product.getEvaluation().getAverageRating());
        document.setMenuId(product.getCategory().getMenu().getId());


        return document;
    }
}
