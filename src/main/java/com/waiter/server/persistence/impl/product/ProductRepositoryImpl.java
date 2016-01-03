package com.waiter.server.persistence.impl.product;

import com.waiter.server.persistence.core.repository.product.ProductRepositoryCustom;
import com.waiter.server.services.group.model.Group;
import com.waiter.server.services.language.model.Language;
import com.waiter.server.services.name.model.Name;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 1/3/2016.
 */
@Component
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> search(String query, double lat, double lon) {
        if (query == null || query.isEmpty()) return null;
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

        List<Product> products = jdbcTemplate.query(sql.toString(), new Object[]{query}, new ProductMapper());

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
            product.setImage(rs.getString("p.image"));
            product.setDescription(rs.getString("p.description"));
            product.setPrice(rs.getDouble("p.price"));

            List<Tag> tags = Tag.parseTags(rs.getString("product_tags"));
            product.setTags(tags);

            Name name = new Name();
            Language language = new Language(rs.getString("n.language"));
            name.setLanguage(language);
            name.setName(rs.getString("n.name"));

            Group group = new Group();
            group.setId(rs.getLong("p.group_id"));
            product.setGroup(group);

            return product;
        }
    }
}
