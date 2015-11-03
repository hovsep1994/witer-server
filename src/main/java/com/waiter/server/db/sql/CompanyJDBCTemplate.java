package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Company;
import com.waiter.server.db.CompanyDAO;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = new StringBuilder("INSERT INTO companies (name, email, phone, password, token, hash)")
                .append(" VALUES (:name, :email, :phone, :password, :token, :hash)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, company.getName());
        params.addValue(EMAIL, company.getMail());
        params.addValue(PHONE, company.getPhone());
        params.addValue(PASSWORD, company.getPassword());
        params.addValue(TOKEN, company.getToken());
        params.addValue(HASH, company.getHash());

        return insertAndGetId(sql, params);
    }


    @Override
    public Company get(int id) {
        String sql = new StringBuilder()
                .append("SELECT * FROM companies ")
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

    @Override
    public boolean validateEmail(String hash) {
        String sql = new StringBuilder()
                .append("SELECT * FROM companies WHERE hash=:hash") //todo select only id
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HASH, hash);
        try {
            jdbcTemplateObject.queryForObject(sql, params, new CompanyRowMapper());
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
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
