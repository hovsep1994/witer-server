package com.waiter.server.persistence.core.repository.product;

import com.waiter.server.services.product.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hovsep on 9/10/16.
 */
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
