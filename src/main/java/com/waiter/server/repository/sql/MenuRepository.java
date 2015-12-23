package com.waiter.server.repository.sql;

import com.waiter.server.commons.entities.*;
import com.waiter.server.repository.MenuDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.waiter.server.repository.sql.CompanyRepository.CompanyRowMapper;

/**
 * Created by Admin on 10/24/2015.
 */
@SuppressWarnings("unchecked")
@Repository
public class MenuRepository extends BaseRepository implements MenuDAO {

    @Override
    public Menu create(Menu menu) {
        String sql = new StringBuilder()
                .append(" INSERT INTO menus ")
                .append(" (name, company_id)")
                .append(" VALUES (:name, :company_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", menu.getName());
        params.addValue("company_id", menu.getCompany().getId());
        menu.setId(insertAndGetId(sql, params));

        return menu;
    }

    @Override
    public Menu get(int id) {
        String sql = new StringBuilder()
                .append(" SELECT")
                .append(" m.id, m.name, m.company_id,")
                .append(" g.id, g.name, g.image,")
                .append(" gtm.group_id, gtm.tag_id,")
                .append(" GROUP_CONCAT(gt.name) AS group_tags,")
                .append(" p.id, p.image, p.price, p.group_id, p.description,")
                .append(" GROUP_CONCAT(pt.name) AS product_tags")
                .append(" n.name, n.entity_type, n.language")
                .append(" FROM menus AS m")
                .append(" LEFT JOIN groups AS g ON(g.menu_id = m.id)")
                .append(" LEFT JOIN group_tag_map AS gtm ON (g.id = gtm.group_id)")
                .append(" LEFT JOIN tags AS gt ON (gt.id = gtm.tag_id)")
                .append(" INNER JOIN products AS p ON (p.group_id = g.id)")
                .append(" LEFT JOIN product_tag_map AS ptm ON (p.id = ptm.product_id)")
                .append(" LEFT JOIN tags AS pt ON (pt.id = ptm.tag_id)")
                .append(" INNER JOIN names AS n ON n.entity_id=p.id AND n.entity_type='product'") //todo add groups also
                .append(" LEFT JOIN companies AS c ON (c.id = m.company_id)")
                .append(" WHERE m.id = '" + id + "'")
                .append(" GROUP BY g.id, p.id")
                .toString();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        Menu menu = (Menu) jdbcTemplateObject.queryForObject(sql, parameterSource, new MenuMapper());
        return menu;
    }

    @Override
    public List<Menu> getCompanyMenus(int companyId) {
        String sql = new StringBuilder().append("SELECT m.id, m.name, m.company_id ")
                .append("FROM menus AS m ")
                .append("WHERE m.company_id = ")
                .append(companyId).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return jdbcTemplateObject.query(sql, parameterSource, new MenuNameMaper());
    }

    private static class MenuMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getMenu(rs);
        }
    }

    private static class MenuNameMaper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Menu()
                    .setId(rs.getInt("m.id"))
                    .setName(rs.getString("m.name"))
                    .setCompany(new Company().setId(rs.getInt("m.company_id")));
        }
    }

    static Menu getMenu(ResultSet rs) throws SQLException {
        Menu menu = new Menu()
                .setId(rs.getInt(ID))
                .setCompany(new CompanyRowMapper().getCompany(rs));

        List<Group> groups = new ArrayList<>();
        do {
            groups.add(new GroupRepository.GroupMapper().getGroup(rs));
        } while (rs.next());
        menu.setGroups(groups);

        return menu;
    }

}
