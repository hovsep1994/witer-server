package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.commons.entities.Tag;
import com.waiter.server.db.ProductDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 10/24/2015.
 */
public class ProductJDBTemplate extends BaseJDBCTemplate implements ProductDAO {

    public ProductJDBTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Product product) {
        String sql = new StringBuilder("INSERT INTO groups (name, image, price, description, group_id) )")
                .append("VALUES (:name, :image, :price, :description, :group_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, product.getName());
        params.addValue(IMAGE, product.getImage());
        params.addValue(PRICE, product.getPrice());
        params.addValue(DESCRIPTION, product.getDescription());
        params.addValue(GROUP_ID, product.getGroup().getId());

        int productId = insertAndGetId(sql, params);
        if (productId != -1 && product.getTags() != null) {
            insertGroupTags(product);
        }

        return productId;
    }

    @Override
    public List<Product> getByGroup(int groupId) {
        String sql = new StringBuilder()
                .append(" SELECT")
                .append(" p.id, p.name, p.image, p.price, p.description, p.group_id,")
                .append(" ptm.product_id, ptm.tag_id, ")
                .append(" GROUP_CONCAT(t.name) AS product_tags")
                .append(" FROM products AS p")
                .append(" LEFT JOIN product_tag_map AS ptm ON (p.id = ptm.product_id)")
                .append(" LEFT JOIN tags AS t ON (t.id = ptm.tag_id)")
                .append(" WHERE p.group_id = '" + groupId + "'")
                .append(" GROUP BY p.id")
                .toString();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        List<Product> products = jdbcTemplateObject.query(sql, parameterSource, new ProductMapper());
        return products;
    }

    @Override
    public Product get(int id) {
        return null;
    }

    private void insertGroupTags(Product product) {
        TagJDBCTemplate tagJDBCTemplate = new TagJDBCTemplate(dataSource);
        List<Integer> tagIds = tagJDBCTemplate.insertAndGetIds(product.getTags());
        insertMappings(MappingTable.PRODUCT_TAG_MAP, product.getId(), tagIds);
    }

    private static class ProductMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getProduct(rs);
        }
    }

    static Product getProduct(ResultSet rs) throws SQLException {
        return new Product()
                .setId(rs.getInt("p.id"))
                .setName(rs.getString("p.name"))
                .setImage(rs.getString("p.image"))
                .setGroup(new Group().setId(rs.getInt("p.group_id")))
                .setDescription(rs.getString("p.description"))
                .setPrice(rs.getDouble("p.price"))
                .setTags(Tag.parseTags(rs.getString("product_tags")));
    }


}
