package com.waiter.server.db.sql;

import javax.sql.DataSource;

import com.waiter.server.commons.entities.Location;
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
                .append(" INSERT INTO venues (country, city, street, zip, latitude, longitude, menu_id)")
                .append(" VALUES (:country, :city, :street, :zip, :latitude, :longitude, :menu_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();

        Location location = venue.getLocation();

        params.addValue("country", location.getCountry());
        params.addValue("city", location.getCity());
        params.addValue("street", location.getStreet());
        params.addValue("zip", location.getZip());
        params.addValue("latitude", location.getLatLng()[0]);
        params.addValue("longitude", location.getLatLng()[1]);

        params.addValue("menu_id", venue.getMenu().getId());

        return insertAndGetId(sql, params);
    }

    public Venue getVenue(int id) {
        String sql = new StringBuilder()
                .append(" SELECT * FROM venues")
                .append(" WHERE id = (:id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(ID, id);
        Venue venue = (Venue) jdbcTemplateObject.queryForObject(sql, params, Venue.class);
        return venue;
    }

}
