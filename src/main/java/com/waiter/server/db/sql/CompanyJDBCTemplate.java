package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Company;
import com.waiter.server.db.CompanyDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 10/24/2015.
 */
public class CompanyJDBCTemplate extends BaseJDBCTemplate implements CompanyDAO {

    public CompanyJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Company company) {
        String sql = new StringBuilder("INSERT INTO company (name, email, phone, menu_id) )")
                .append("VALUES (:name, :email, :phone, :menu_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, company.getName());
        params.addValue(EMAIL, company.getMail());
        params.addValue(PHONE, company.getPhone());
        params.addValue(MENU_ID, company.getMenu().getId());

        return insertAndGetId(sql, params);
    }

    @Override
    public Company get(int id) {
        String sql = new StringBuilder()
                .append("SELECT * FROM company ")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(ID, id);
        Company company = (Company) jdbcTemplateObject.query(sql, params, new CompanyRowMapper());
        return company;
    }

    @Override
    public List<Company> search(String s) {
        String sql = new StringBuilder()
                .append("SELECT * FROM company ")
                .append("name LIKE '%" + s + "%'")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Company> companyList = jdbcTemplateObject.queryForList(sql, params, Company.class);
        return companyList;
    }

    private static class CompanyRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Company company = new Company()
                    .setId(rs.getInt(ID))
                    .setName(rs.getString(NAME))
                    .setMail(rs.getString(EMAIL))
                    .setPhone(rs.getString(PHONE));
            return company;
        }
    }
}
