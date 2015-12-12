package com.waiter.server.services.product.impl;

import com.waiter.server.commons.entities.Product;
import com.waiter.server.repository.sql.ProductRepository;
import com.waiter.server.services.name.NameService;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private NameService nameService;
    @Autowired
    private TagService tagService;

    @Override
    public Product create(Product product) {
        Product p = productRepository.create(product);
        nameService.create(p.getName());
        if (p.getTags() != null) {
            tagService.batchInsert(p.getTags());
        }
        return p;
    }

    @Override
    public void remove(Long productId) {
        productRepository.remove(productId.intValue());
    }

    @Override
    public void update(Product product) {
        productRepository.update(product);
    }

    @Override
    public List<Product> getByGroup(Long groupId) {
        return productRepository.getByGroup(groupId.intValue());
    }

    @Override
    public Product get(Long id) {
        return productRepository.get(id.intValue());
    }

    @Override
    public List<Product> search(String query, double lat, double lon) {
        return productRepository.search(query, lat, lon);
    }
}
