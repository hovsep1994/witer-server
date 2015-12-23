package com.waiter.server.services.product;

import com.waiter.server.commons.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface ProductService {

    Product create(Product product);
    void remove(Long productId);
    void update(Product product);
    List<Product> getByGroup(Long groupId);
    Product get(Long id);
    List<Product> search(String query, double lat, double lon);

}
