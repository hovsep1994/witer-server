package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.commons.entities.Menu;
import com.waiter.server.commons.entities.Product;
import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.MenuDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.waiter.server.db.sql.CompanyJDBCTemplate.CompanyRowMapper;

/**
 * Created by Admin on 10/24/2015.
 */
public class MenuJDBCTemplate extends BaseJDBCTemplate implements MenuDAO {

    public MenuJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Menu menu) {
        String sql = new StringBuilder()
                .append(" INSERT INTO menu )")
                .append(" (name, company_id)")
                .append(" VALUES (:name, :company_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", menu.getName());
        params.addValue("company_id", menu.getCompany().getId());

        return insertAndGetId(sql, params);
    }

    @Override
    public Menu get(int id) {
        String sql = new StringBuilder()
                .append(" SELECT")
                .append(" m.id, m.name, m.company_id,")
                .append(" g.id, g.name, g.image,")
                .append(" gtm.group_id, gtm.tag_id,")
                .append(" GROUP_CONCAT(gt.name) AS group_tags,")
                .append(" p.id, p.name, p.image, p.price, p.group_id, p.description,")
                .append(" GROUP_CONCAT(pt.name) AS product_tags")
                .append(" FROM menus AS m")
                .append(" LEFT JOIN groups AS g ON(g.menu_id = m.id)")
                .append(" LEFT JOIN group_tag_map AS gtm ON (g.id = gtm.group_id)")
                .append(" LEFT JOIN tags AS gt ON (gt.id = gtm.tag_id)")
                .append(" INNER JOIN products AS p ON (p.group_id = g.id)")
                .append(" LEFT JOIN product_tag_map AS ptm ON (p.id = ptm.product_id)")
                .append(" LEFT JOIN tags AS pt ON (pt.id = ptm.tag_id)")
                .append(" LEFT JOIN companies AS c ON (c.id = m.company_id)")
                .append(" WHERE m.id = '" + id + "'")
                .append(" GROUP BY g.id, p.id")
                .toString();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        Menu menu = (Menu) jdbcTemplateObject.queryForObject(sql, parameterSource, new MenuMapper());
        return menu;
    }

    private static class MenuMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getMenu(rs);
        }
    }

    static Menu getMenu(ResultSet rs) throws SQLException {
        Menu menu = new Menu()
                .setId(rs.getInt(ID))
                .setCompany(new CompanyRowMapper().getCompany(rs));

        List<Group> groups = new ArrayList<>();
        do {
            groups.add(GroupJDBCTemplate.getGroup(rs));
        } while (rs.next());
        menu.setGroups(groups);

        return menu;
    }

}
