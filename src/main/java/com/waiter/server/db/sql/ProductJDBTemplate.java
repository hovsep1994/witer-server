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
    public int create(Product product, String language, String type) {
        String sql = new StringBuilder()
                .append(" INSERT INTO products (image, price, description, group_id)")
                .append(" VALUES (:image, :price, :description, :group_id)")
                .toString();

        String sqlName = new StringBuilder()
                .append(" INSERT INTO product_names (name, language, product_id, type)")
                .append(" VALUES (:name, :language, :product_id, :type)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IMAGE, product.getImage());
        params.addValue(PRICE, product.getPrice());
        params.addValue(DESCRIPTION, product.getDescription());
        params.addValue(GROUP_ID, product.getGroup().getId());


        int productId = insertAndGetId(sql, params);
        if (productId != -1) {

            MapSqlParameterSource paramsName = new MapSqlParameterSource();
            paramsName.addValue(NAME, product.getName());
            paramsName.addValue("product_id", productId);
            paramsName.addValue("language", language);
            paramsName.addValue("type", type);

            insertAndGetId(sqlName, paramsName);

            if (product.getTags() != null) {
                insertProductTags(product);
            }
        }

        return productId;
    }

    @Override
    public void remove(int productId) {
        removeRow(productId, "products");
    }

    @Override
    public void update(Product product) {
        String sql = new StringBuilder().append("UPDATE products SET ")
                .append("image=:image, price=:price, ")
                .append("description=:description, group_id=:groupId")
                .append("WHERE id=").append(product.getId()).toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IMAGE, product.getImage());
        params.addValue(PRICE, product.getPrice());
        params.addValue(DESCRIPTION, product.getDescription());
        params.addValue(GROUP_ID, product.getGroup().getId());
        jdbcTemplateObject.update(sql, params);
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

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", query);

        List<Product> products = jdbcTemplateObject.query(sql.toString(), params, new ProductMapper());

        return products;
    }

    private void insertProductTags(Product product) {
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
                .setName(rs.getString("pn.name"))
                .setImage(rs.getString("p.image"))
                .setGroup(new Group().setId(rs.getInt("p.group_id")))
                .setDescription(rs.getString("p.description"))
                .setPrice(rs.getDouble("p.price"))
                .setTags(Tag.parseTags(rs.getString("product_tags")));
    }


}
