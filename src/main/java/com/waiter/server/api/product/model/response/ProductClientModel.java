package com.waiter.server.api.product.model.response;

import com.waiter.server.api.product.model.ProductPriceModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 8/19/16.
 */
public class ProductClientModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductClientModel.class);

    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String image;
    private Set<String> tags;
    private Set<ProductPriceModel> productPrices;
    private Integer rated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<ProductPriceModel> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(Set<ProductPriceModel> productPrices) {
        this.productPrices = productPrices;
    }

    public Integer getRated() {
        return rated;
    }

    public void setRated(Integer rated) {
        this.rated = rated;
    }

    public static List<ProductClientModel> convert(Collection<Product> products, Language language) {
        return products.stream().map(product -> {
            try {
                return convert(product, language);
            } catch (Exception e) {
                LOGGER.error("Failed to covert product. {} ", product.getId());
                return null;
            }
        }).filter(x -> x != null).collect(Collectors.toList());
    }

    public static ProductClientModel convert(Product product, Language language) {
        final ProductClientModel productModel = new ProductClientModel();
        productModel.setId(product.getId());
        productModel.setDescription(product.getDescriptionTranslation(Arrays.asList(language, Language.en)).getText());
        productModel.setName(product.getNameTranslation(Arrays.asList(language, Language.en)).getText());
        productModel.setProductPrices(ProductPriceModel
                .convert(product.getProductPrices(), language, product.getCategory().getMenu().getCurrency()));
        productModel.setTags(product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        productModel.setImage(ImageUrlGenerator.getUrl(EntityType.PRODUCT, product.getGallery()));
        productModel.setRating(product.getAverageRating());

        return productModel;
    }
}
