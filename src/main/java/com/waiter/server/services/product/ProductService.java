package com.waiter.server.services.product;

import com.waiter.server.services.product.dto.AddProductDto;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.dto.ProductSearchParameters;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface ProductService {

    Product getById(Long id);

    List<Product> getByGroupId(Long groupId);

    Product create(Long groupId, AddProductDto addProductDto);

    void remove(Long productId);

    List<Product> search(ProductSearchParameters productSearchParams);

}
