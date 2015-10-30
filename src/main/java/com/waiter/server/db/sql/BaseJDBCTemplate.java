package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.City;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class BaseJDBCTemplate {

    protected static final String DELIMITER_SPACE = " ";
    protected static final String ID = "id";
    protected static final String NAME = "name";
    protected static final String CODE = "code";
    protected static final String PASSWORD = "password";
    protected static final String TOKEN = "token";
    protected static final String EMAIL = "email";
    protected static final String PHONE = "phone";
    protected static final String TAGS = "tags";
    protected static final String IMAGE = "image";
    protected static final String MENU_ID = "menu_id";
    protected static final String GROUP_ID = "group_id";
    protected static final String DESCRIPTION = "description";
    protected static final String PRICE = "price";

    protected DataSource dataSource;
    protected NamedParameterJdbcTemplate jdbcTemplateObject;

    public BaseJDBCTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new NamedParameterJdbcTemplate(this.dataSource);
    }

    protected int insertAndGetId(String sql, MapSqlParameterSource params) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(sql, params, holder);
        Number key = holder.getKey();
        return key == null ? -1 : key.intValue();
    }

    public void insertMappings(MappingTable table, final int id, final List<Integer> ids) {
        String sql = new StringBuilder()
                .append(" INSERT INTO ").append(table.getName())
                .append(" (").append(table.getSecondColumn())
                .append(", ").append(table.getSecondColumn())
                .append(") VALUES (?,?) ")
                .toString();
        jdbcTemplateObject.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, id);
                ps.setInt(2, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

    }

    protected enum MappingTable {

        COMPANY_VENUE_MAP("company_venue_map","company_id","venue_id"),
        MENU_GROUP_MAP("menu_group_map","menu_id","group_id"),
        GROUP_TAG_MAP("group_tag_map", "group_id", "tag_id"),
        GROUP_PRODUCT_MAP("group_product_map","group_id","product_id"),
        PRODUCT_TAG_MAP("product_tag_map","product_id","tag_id");

        private String tableName;
        private String firstColumn;
        private String secondColumn;

        MappingTable(String tableName, String firstColumn, String secondColumn) {
            this.tableName = tableName;
            this.firstColumn = firstColumn;
            this.secondColumn = secondColumn;
        }


        public String getName() {
            return tableName;
        }

        public void setName(String tableName) {
            this.tableName = tableName;
        }

        public String getFirstColumn() {
            return firstColumn;
        }

        public void setFirstColumn(String firstColumn) {
            this.firstColumn = firstColumn;
        }

        public String getSecondColumn() {
            return secondColumn;
        }

        public void setSecondColumn(String secondColumn) {
            this.secondColumn = secondColumn;
        }
    }

    protected static class CityMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            City city = new City();
            city.setName(rs.getString("name"));
            return city;
        }
    }

}
