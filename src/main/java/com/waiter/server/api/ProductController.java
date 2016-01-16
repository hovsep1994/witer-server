package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.create(product));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(productService.get(id));
    }

    @RequestMapping(name = "heartbeat", method = RequestMethod.GET)
    public ResponseEntity<String> heartbeat() {
        return new ResponseEntity<>("ok");
    }
}
