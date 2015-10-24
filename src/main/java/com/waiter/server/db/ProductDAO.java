package com.waiter.server.db;

import com.waiter.server.commons.entities.Product;

/**
 * Created by Admin on 10/24/2015.
 */
public interface ProductDAO {
    int create(Product product);
}
