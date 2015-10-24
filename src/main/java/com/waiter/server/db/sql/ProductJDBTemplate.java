package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.db.ProductDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
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

    private void insertGroupTags(Product product) {
        TagJDBCTemplate tagJDBCTemplate = new TagJDBCTemplate(dataSource);
        List<Integer> tagIds = tagJDBCTemplate.insertAndGetIds(product.getTags());
        insertMappings(MappingTable.PRODUCT_TAG_MAP, product.getId(), tagIds);
    }
}
