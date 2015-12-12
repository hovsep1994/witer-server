package com.waiter.server.repository;

import com.waiter.server.commons.entities.Product;

import java.util.List;

public interface ProductDAO {
    Product create(Product product);
    void remove(int productId);
    void update(Product product);
    List<Product> getByGroup(int groupId);
    Product get(int id);
    List<Product> search(String query, double lat, double lon);
}
