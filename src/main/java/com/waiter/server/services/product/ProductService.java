package com.waiter.server.services.product;

import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.dto.ProductSearchParameters;

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

    List<Product> search(ProductSearchParameters productSearchParams);

}
