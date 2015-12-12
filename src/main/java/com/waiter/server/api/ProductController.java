package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestPart("image") MultipartFile imagePart, @RequestPart("product") Product product) {
        productService.create(product); //todo handle image
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(productService.get(id));
    }
}
