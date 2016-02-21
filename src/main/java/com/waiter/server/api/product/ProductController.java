package com.waiter.server.api.product;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product createdProduct = productService.create(product);
        return ResponseEntity.forResponse(createdProduct);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findOne(@PathVariable Long id) {
        return ResponseEntity.forResponse(productService.get(id));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> search(@RequestBody ProductSearchParameters productSearchParams) {
        List<Product> products = productService.search(productSearchParams);
        return ResponseEntity.forResponse(products);
    }
}