package com.waiter.server.api.product.model;

import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.model.ProductPrice;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 9/10/16.
 */
public class ProductPriceModel {
    private Long id;
    private String name;
    private Double price;
    private String currency;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static ProductPriceModel convert(ProductPrice productPrice, Language language, Currency currency) {
        final ProductPriceModel productPriceModel = new ProductPriceModel();
        productPriceModel.setId(productPrice.getId());
        productPriceModel.setName(productPrice.getNameByLangauge(language));
        productPriceModel.setPrice(productPrice.getPrice());
        if (currency != null) {
            productPriceModel.setCurrency(currency.getSymbol());
        }
        return productPriceModel;
    }

    public static Set<ProductPriceModel> convert(Set<ProductPrice> productPrices, Language language, Currency currency) {
        return productPrices.stream().map(productPrice -> convert(productPrice, language, currency)).collect(Collectors.toSet());
    }

    public static Set<ProductPriceModel> convert(Set<ProductPrice> productPrices, Language language) {
        return convert(productPrices, language, null);
    }

}
