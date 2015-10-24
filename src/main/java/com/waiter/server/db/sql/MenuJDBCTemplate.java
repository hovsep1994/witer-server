package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Menu;
import com.waiter.server.db.MenuDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;

/**
 * Created by Admin on 10/24/2015.
 */
public class MenuJDBCTemplate extends BaseJDBCTemplate implements MenuDAO {

    public MenuJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Menu menu) {
        String sql = new StringBuilder("INSERT INTO menu )")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();

        return insertAndGetId(sql, params);    }
}
