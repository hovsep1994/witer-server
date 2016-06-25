package com.waiter.server.persistence.impl.product;

import com.waiter.server.persistence.core.repository.product.ProductRepositoryCustom;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import org.hibernate.Criteria;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by Admin on 1/3/2016.
 */
@Component
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private static final double DISTANCE = 10;
    private static final double DEG = 111;

    private static final String PARAMETER_LATITUDE = "latitude";
    private static final String PARAMETER_LONGITUDE = "longitude";
    private static final String PARAMETER_NAME = "name";

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


    public List<Product> findBySearchParameters(ProductSearchParameters parameters) {
        Assert.notNull(parameters, "ProductSearchParameters must not be null");
        Assert.notNull(parameters.getName(), "name must not be null");
        final TypedQuery<Product> typedQuery = buildFindProductsTypedQuery(parameters, Product.class);

        List<Product> products = typedQuery.getResultList();
        return products;
    }

    private <T> TypedQuery<T> buildFindProductsTypedQuery(final ProductSearchParameters parameters, final Class<T> queryResultType) {
        final String queryString = buildFindProductsQueryString(parameters);
        // Create typed query
        final TypedQuery<T> typedQuery = entityManager.createQuery(queryString, queryResultType);
        // Set parameters
        if (parameters.getName() != null && !parameters.getName().isEmpty()) {
            typedQuery.setParameter(PARAMETER_NAME, parameters.getName());
        }

        typedQuery.setParameter(PARAMETER_LATITUDE, parameters.getLatitude());
        typedQuery.setParameter(PARAMETER_LONGITUDE, parameters.getLongitude());

        return typedQuery;
    }

    private String buildFindProductsQueryString(ProductSearchParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" SELECT p ");
        queryBuilder.append(" FROM Product p ");
        queryBuilder.append(" INNER JOIN p.company ");
        queryBuilder.append(" LEFT JOIN p.location ");

        queryBuilder.append(" WHERE ");
        queryBuilder.append(" ABS(v.location.latitude - " + parameters.getLatitude() + ") < '" + DISTANCE / DEG + "'");
        queryBuilder.append(" AND ABS(v.location.longitude - " + parameters.getLongitude() + ") < '" + DISTANCE / (Math.cos(Math.toRadians(parameters.getLatitude())) * DEG) + "'");

        if (parameters.getName() != null && !parameters.getName().isEmpty()) {
            queryBuilder.append(" AND MATCH(c.name) AGAINST(:" + PARAMETER_NAME + " IN BOOLEAN MODE) ");
        }

        queryBuilder.append(" ORDER BY SQRT(POW(v.latitude - :" + PARAMETER_LATITUDE + ",2) + POW(v.longitude - " + PARAMETER_LONGITUDE + ", 2) )");

        return queryBuilder.toString();
    }

    @Override
    public List<Product> search(String query, double lat, double lon) {
        if (query == null || query.isEmpty()) {
            return null;
        }

        query = query.replaceAll("\\s", "* ");

        StringBuilder sql = new StringBuilder()
                .append(" SELECT")
                .append(" SUM(MATCH(pn.name)AGAINST(:query IN BOOLEAN MODE) )AS nameScore,")
                .append(" SUM(MATCH(pt.name)AGAINST(:query IN BOOLEAN MODE)) AS ptagsScore,")
                .append(" SUM(MATCH(gt.name)AGAINST(:query IN BOOLEAN MODE)) AS gtagsScore,")
                .append(" pn.product_id, pn.name, pn.language,")
                .append(" p.id, p.image, p.price, p.description, p.group_id,")
                .append(" ptm.product_id, ptm.tag_id,")
                .append(" pt.id, pt.name,")
                .append(" g.id, g.menu_id,")
                .append(" gt.id, gt.name,")
                .append(" v.id, v.country, v.city, v.street, v.zip, v.latitude, v.longitude, v.company_id,")
                .append(" GROUP_CONCAT(pt.name) AS product_tags,")
                .append(" GROUP_CONCAT(gt.name) AS group_tags")
                .append(" FROM product_names AS pn")
                .append(" INNER JOIN products AS p ON p.id = pn.product_id")
                .append(" LEFT JOIN product_tag_map as ptm ON ptm.product_id = p.id")
                .append(" LEFT JOIN tags AS pt ON (pt.id = ptm.tag_id)")
                .append(" INNER JOIN groups AS g ON g.id = p.group_id")
                .append(" LEFT JOIN group_tag_map as gtm ON gtm.group_id = g.id")
                .append(" LEFT JOIN tags AS gt ON (gt.id = gtm.tag_id)")
                .append(" INNER JOIN venues AS v ON v.menu_id = g.menu_id")
                .append(" WHERE")
                .append(" MATCH(pn.name) AGAINST(:query IN BOOLEAN MODE)")
                .append(" OR MATCH (pt.name) AGAINST(:query IN BOOLEAN MODE)")
                .append(" OR MATCH (gt.name) AGAINST(:query IN BOOLEAN MODE)")
                .append(" GROUP BY p.id, v.id")
                .append(" ORDER BY")
                .append(" nameScore DESC,")
                .append(" ptagsScore DESC,")
                .append(" gtagsScore DESC,")
                .append(" SQRT(POW(v.latitude - " + lat + ",2) + POW(v.longitude - " + lon + ", 2) )");

//        List<Product> products = entityManager.query(sql.toString(), new Object[]{query}, new ProductMapper());
        Query nativeQuery = entityManager.createNativeQuery(sql.toString(), Product.class);
        List<Product> products = nativeQuery.getResultList();

        return products;
    }

    public static class ProductMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getProduct(rs);
        }

        public Product getProduct(ResultSet rs) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("p.id"));
//            product.setImage(rs.getString("p.image"));
//            product.setDescription(rs.getString("p.description"));
            product.setPrice(rs.getDouble("p.price"));

            Set<Tag> tags = Tag.parseTags(rs.getString("product_tags"));
            product.setTags(tags);

            Translation translation = new Translation();
//            Language language = new Language(rs.getString("n.language"));
//            translation.setLanguage(language);
            translation.setName(rs.getString("n.translation"));

            Category category = new Category();
            category.setId(rs.getLong("p.group_id"));
            product.setCategory(category);

            return product;
        }
    }
}
