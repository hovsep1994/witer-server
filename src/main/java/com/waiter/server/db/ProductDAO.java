package com.waiter.server.db;

import com.waiter.server.commons.entities.Product;

import java.util.List;

/**
 * Created by Admin on 10/24/2015.
 */
public interface ProductDAO {
    int create(Product product);
    List<Product> getByGroup(int groupId);
    Product get(int id);

}
