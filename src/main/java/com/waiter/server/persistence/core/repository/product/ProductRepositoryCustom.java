package com.waiter.server.persistence.core.repository.product;

import com.waiter.server.services.product.model.Product;

import java.util.List;

/**
 * Created by Admin on 1/3/2016.
 */
public interface ProductRepositoryCustom {

    Product setRatingByCustomerToken(Long productId, String customerToken, Integer rating);

    List<Product> findByIdAndCustomerToken(Long productId,String customerToken);

    List<Product> findTopProducts(Long menuId, int offset, int limit);

}
