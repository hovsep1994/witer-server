package com.waiter.server.services.product.impl;

import com.waiter.server.persistence.core.repository.product.ProductRepository;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.name.NameService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private TagService tagService;

    @Override
    public Product create(Product product) {
        Assert.notNull(product, "Product must not be null");
        Product createdProduct = productRepository.save(product);
        return createdProduct;
    }

    @Override
    public void remove(Long productId) {
        productRepository.delete(productId);
    }

    @Override
    public Product update(Product product) {
        Assert.notNull(product, "product must not be null");
        Assert.notNull(product.getId(), "product id must not be null");
        /*
         * checking does product with id exist
         */
        get(product.getId());
        product.setUpdated(new Date());
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @Override
    public List<Product> getByGroup(Long groupId) {
        Assert.notNull(groupId, "group id must not be null");
        List<Product> products = productRepository.findByGroupId(groupId);
        return products;
    }

    @Override
    public Product get(Long id) {
        Assert.notNull(id, "id must not be null");
        Product product = productRepository.findOne(id);
        if (product == null) {
            LOGGER.debug("Product with id -{} not found", id);
            throw new ServiceRuntimeException("Product not found");
        }
        return product;
    }

    @Override
    public List<Product> search(ProductSearchParameters params) {
        List<Product> products = productRepository.search(
                params.getName(), params.getLatitude(), params.getLongitude());
        return products;
    }
}
