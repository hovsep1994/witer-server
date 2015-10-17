package com.waiter.server.db.sql;

import javax.sql.DataSource;

import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.VenueDAO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Created by shahenpoghosyan on 7/14/15.
 */
public class VenueJDBCTemplate extends BaseJDBCTemplate implements VenueDAO {

    private static final Logger LOG = Logger.getLogger(VenueJDBCTemplate.class);


    public VenueJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Venue venue) {
        String sql = new StringBuilder()
                .append(" INSERT INTO venues")
                .append(" (name,code,owner_email) VALUES (:name,:code,:email)")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, venue.getName());
        params.addValue(CODE, venue.getCode());
        params.addValue(EMAIL, venue.getOwnerEmail());

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(sql, params, holder);
        venue.setId(holder.getKey().intValue());
        return venue.getId();
    }

}
