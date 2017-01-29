package com.waiter.server.persistence.impl.product;

import com.waiter.server.persistence.core.repository.product.ProductRepositoryCustom;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 1/3/2016.
 */
@Component
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Product setRatingByCustomerToken(Long productId, String customerToken, Integer rating) {
        String queryString = new StringBuilder()
                .append(" UPDATE rate r")
                .append(" JOIN evaluation e ON e.id = r.evaluation_id")
                .append(" JOIN product p ON evaluation_id = e.id")
                .append(" WHERE p.id = " + productId)
                .append(" AND customerToken = " + customerToken)
                .append(" SET r.rating = " + rating)
                .toString();
        final TypedQuery<Product> typedQuery = entityManager.createQuery(queryString, Product.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Product> findByIdAndCustomerToken(Long productId, String customerToken) {
        String queryString = new StringBuilder()
                .append(" Select p from Product p")
                .append(" join p.evaluation e")
                .append(" join p.evaluation.rates r")
                .append(" where p.id = ").append(productId)
                .append(" and r.customerToken = '").append(customerToken).append("'")
                .toString();
        final TypedQuery<Product> typedQuery = entityManager.createQuery(queryString, Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> findTopProducts(Long menuId, int offset, int limit) {
        String queryString = new StringBuilder()
                .append(" SELECT p FROM Product p")
                .append(" join p.evaluation")
                .append(" join p.category")
                .append(" join p.category.menu")
                .append(" WHERE p.category.menu.id = ").append(menuId)
                .append(" ORDER BY p.evaluation.rateSum")
                .append(" ").append(offset).append(",").append(limit)
                .toString();
        final TypedQuery<Product> typedQuery = entityManager.createQuery(queryString, Product.class);
        return typedQuery.getResultList();
    }

}
