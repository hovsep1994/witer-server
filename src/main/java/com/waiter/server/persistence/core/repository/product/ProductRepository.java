package com.waiter.server.persistence.core.repository.product;

import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findByCategoryId(Long groupId);

    Gallery findGalleryById(Long id);

}
