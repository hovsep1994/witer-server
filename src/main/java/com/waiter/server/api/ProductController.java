package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.commons.entities.Gallery;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.services.gallery.GalleryService;
import com.waiter.server.services.product.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

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
