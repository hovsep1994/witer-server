package com.waiter.server.persistence.core.repository.product;

import com.waiter.server.services.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByGroupId(Long groupId);

    //TODO need to write custom query
    List<Product> search(String query, double lat, double lon);
}
