package com.waiter.server.persistence.core.repository.product;

import com.waiter.server.services.product.model.Product;

import java.util.List;

/**
 * Created by Admin on 1/3/2016.
 */
public interface ProductRepositoryCustom {

    List<Product> search(String name, double lat, double lon);

}
