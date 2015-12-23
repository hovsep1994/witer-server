package com.waiter.server.repository.sql;

import com.waiter.server.commons.entities.Company;
import com.waiter.server.repository.CompanyDAO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 10/24/2015.
 */
@Repository
public class CompanyRepository extends BaseRepository implements CompanyDAO {

    private static final Logger logger = Logger.getLogger(CompanyRepository.class);

    @Override
    public Company create(Company company) {
        String sql = new StringBuilder("INSERT INTO companies (name, phone)")
                .append(" VALUES (:name, :phone)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, company.getName());
        params.addValue(PHONE, company.getPhone());

        company.setId(insertAndGetId(sql, params));
        return company;
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
