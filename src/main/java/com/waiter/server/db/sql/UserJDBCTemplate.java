package com.waiter.server.db.sql;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;
import com.waiter.server.commons.entities.User;
import com.waiter.server.db.CompanyDAO;
import com.waiter.server.db.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.waiter.server.commons.ErrorCodes.FAILED_AUTHENTICATION;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * @author shahenpoghosyan
 */
public class UserJDBCTemplate extends BaseJDBCTemplate implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserJDBCTemplate.class);
    private CompanyDAO companyDAO;

    public UserJDBCTemplate(DataSource dataSource) {
        super(dataSource);
        companyDAO = new CompanyJDBCTemplate(dataSource);
    }

    @Override
    public long create(User user) {
        String sql = new StringBuilder("INSERT INTO users (name, email, password, token, hash, company_id)")
                .append(" VALUES (:name, :email, :password, :token, :hash, :company_id)")
                .toString();

        int companyId = companyDAO.create(user.getCompany());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, user.getName());
        params.addValue(EMAIL, user.getEmail());
        params.addValue(PASSWORD, user.getPassword());
        params.addValue(TOKEN, user.getToken());
        params.addValue(HASH, user.getHash());
        params.addValue(COMPANY_ID, companyId);

        return insertAndGetId(sql, params);
    }

    @Override
    public User login(String login, String password) throws APIException {
        String sql = new StringBuilder()
                .append("SELECT * FROM users AS u WHERE email=:email AND password=:password")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(EMAIL, login);
        params.addValue(PASSWORD, password);
        try {
            return (User) jdbcTemplateObject.queryForObject(sql, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new APIException(SC_UNAUTHORIZED,
                    new APIError(FAILED_AUTHENTICATION, "Provided login and password does not match. "));
        }
    }

    @Override
    public User authenticate(String key) throws APIException {
        String sql = new StringBuilder()
                .append("SELECT * FROM users AS u WHERE token=:token") //todo select only id
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(TOKEN, key);
        try {
            return (User) jdbcTemplateObject.queryForObject(sql, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new APIException(SC_UNAUTHORIZED,
                    new APIError(FAILED_AUTHENTICATION, "Company with this token doesn't exist. "));
        }
    }

    @Override
    public boolean validateEmail(String hash) {
        String sql = new StringBuilder()
                .append("UPDATE users SET validated=1 WHERE hash=:hash")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HASH, hash);
        return jdbcTemplateObject.update(sql, params) != 0;
    }

    static class UserRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getUser(rs);
        }

        User getUser(ResultSet rs) throws SQLException {
            return new User()
                    .setId(rs.getInt("u.id"))
                    .setName(rs.getString("u.name"))
                    .setEmail(rs.getString("u.email"))
                    .setToken(rs.getString("u.token"))
                    .setCompany(new Company().setId(rs.getInt("u.company_id")));
        }
    }
}
