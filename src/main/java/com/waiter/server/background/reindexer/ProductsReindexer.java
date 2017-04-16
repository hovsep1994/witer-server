package com.waiter.server.background.reindexer;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Component
@Transactional
public class ProductsReindexer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsReindexer.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSearchService productSearchService;

    public void reindexAll() {
        int i = 0;
        List<Product> products;
        while (!(products = productService.getProducts(i++, 1000)).isEmpty()) {

            products.forEach(product -> {
                try {
                    productSearchService.addOrUpdate(product);
                } catch (Exception e) {
                    LOGGER.error("Error saving product in solr. ", e);
                }
            });
            LOGGER.info("Products batch was indexed. ");
        }
    }


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        ProductsReindexer reindexer = context.getBean("productsReindexer", ProductsReindexer.class);
        reindexer.reindexAll();
    }
}
