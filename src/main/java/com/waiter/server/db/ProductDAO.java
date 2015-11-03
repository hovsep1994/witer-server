package com.waiter.server.db;

import com.waiter.server.commons.entities.Product;

import java.util.List;

public interface ProductDAO {
    int create(Product product);
    List<Product> getByGroup(int groupId);
    Product get(int id);
    List<Product> search(String query, int limit);
}
