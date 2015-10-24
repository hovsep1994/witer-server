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
    protected static final String NAME = "name";
    protected static final String CODE = "code";
    protected static final String EMAIL = "email";
    protected static final String TAGS = "tags";
    protected static final String IMAGE = "image";
    protected static final String MENU_ID = "menu_id";

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

        GROUP_TAG_MAP("group_tag_map", "group_id", "tag_id");

        private String name;
        private String firstColumn;
        private String secondColumn;

        MappingTable(String name, String firstColumn, String secondColumn) {
            this.name = name;
            this.firstColumn = firstColumn;
            this.secondColumn = secondColumn;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
