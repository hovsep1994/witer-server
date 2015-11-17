package com.waiter.server.db.sql;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.db.CompanyDAO;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static com.waiter.server.commons.ErrorCodes.FAILED_AUTHENTICATION;

/**
 * Created by Admin on 10/24/2015.
 */
public class CompanyJDBCTemplate extends BaseJDBCTemplate implements CompanyDAO {

    private static final Logger logger = Logger.getLogger(CompanyJDBCTemplate.class);

    public CompanyJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Company company) {
        String sql = new StringBuilder("INSERT INTO companies (name, phone)")
                .append(" VALUES (:name, :phone)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, company.getName());
        params.addValue(PHONE, company.getPhone());

        return insertAndGetId(sql, params);
    }

    @Override
    public Company get(int id) {
        String sql = new StringBuilder()
                .append("SELECT * FROM companies AS c ")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(ID, id);
        Company company = (Company) jdbcTemplateObject.query(sql, params, new CompanyRowMapper());
        return company;
    }

    @Override
    public List<Company> search(String s) {
        String sql = new StringBuilder()
                .append("SELECT * FROM companies ")
                .append("name LIKE '%" + s + "%'")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Company> companyList = jdbcTemplateObject.queryForList(sql, params, Company.class);
        return companyList;
    }

    static class CompanyRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getCompany(rs);
        }

        Company getCompany(ResultSet rs) throws SQLException {
            return new Company()
                    .setId(rs.getInt("c.id"))
                    .setName(rs.getString("c.name"))
                    .setPhone(rs.getString("c.phone"));
        }
    }
}
