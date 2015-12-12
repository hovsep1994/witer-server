package com.waiter.server.repository.sql;

import com.waiter.server.commons.entities.*;
import com.waiter.server.repository.GroupDAO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.waiter.server.repository.sql.ProductRepository.ProductMapper;

/**
 * @author shahenpoghosyan
 */
public class GroupRepository extends BaseRepository implements GroupDAO {

    private static final Logger LOG = Logger.getLogger(GroupRepository.class);

    @Override
    public Group create(Group group) {
        String sql = new StringBuilder()
                .append(" INSERT INTO groups (name, image, menu_id)")
                .append(" VALUES (:name, :image, :menu_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, group.getName());
        params.addValue(IMAGE, group.getImage());
        params.addValue(MENU_ID, group.getMenu().getId());
        int groupId = insertAndGetId(sql, params);
        group.setId(groupId);
        return group;
    }

    @Override
    public void remove(int groupId) {
        removeRow(groupId, "groups");
    }

    @Override
    public void update(Group group) {
        String sql = new StringBuilder().append("UPDATE groups SET ")
                .append("name=:name. image=:image")
                .append(" WHERE id=").append(group.getId()).toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, group.getName());
        params.addValue(IMAGE, group.getImage());
        jdbcTemplateObject.update(sql, params);
    }

    @Override
    public Group get(int id) {
        String sql = new StringBuilder()
                .append(" SELECT")
                .append(" g.id, g.name, g.image,")
                .append(" gtm.group_id, gtm.tag_id,")
                .append(" GROUP_CONCAT(gt.name) AS group_tags,")
                .append(" p.id, p.name, p.image, p.price, p.group_id, p.description,")
                .append(" GROUP_CONCAT(pt.name) AS product_tags")
                .append(" FROM groups AS g")
                .append(" LEFT JOIN group_tag_map AS gtm ON (g.id = gtm.group_id)")
                .append(" LEFT JOIN tags AS gt ON (gt.id = gtm.tag_id)")
                .append(" INNER JOIN products AS p ON (p.group_id = g.id)")
                .append(" LEFT JOIN product_tag_map AS ptm ON (p.id = ptm.product_id)")
                .append(" LEFT JOIN tags AS pt ON (pt.id = ptm.tag_id)")
                .append(" WHERE g.id = '" + id + "'")
                .append(" GROUP BY g.id, p.id")
                .toString();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        Group group = (Group) jdbcTemplateObject.queryForObject(sql, parameterSource, new GroupMapper());
        return group;
    }

    private static class GroupMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getGroup(rs);
        }

        public Group getGroup(ResultSet rs) throws SQLException {
            Group group = new Group()
                    .setId(rs.getInt(ID))
                    .setName(new Name()
                            .setLanguage(new Language(rs.getString("n.language")))
                            .setName(rs.getString("n.name")))
                    .setImage(rs.getString(IMAGE))
                    .setTags(Tag.parseTags(rs.getString("group_tags")));

            List<Product> products = new ArrayList<>();
            do {
                products.add(new ProductMapper().getProduct(rs));
            } while (rs.next());
            group.setProducts(products);
            return group;
        }
    }

}
