package com.waiter.server.db.sql;

import javax.sql.DataSource;

import com.waiter.server.commons.entities.Location;
import com.waiter.server.commons.entities.Venue;
import com.waiter.server.db.VenueDAO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shahenpoghosyan on 7/14/15.
 */
public class VenueJDBCTemplate extends BaseJDBCTemplate implements VenueDAO {

    private static final Logger logger = Logger.getLogger(VenueJDBCTemplate.class);


    public VenueJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Venue venue) {
        String sql = new StringBuilder()
                .append(" INSERT INTO venues (country, city, street, zip, latitude, longitude, menu_id, company_id)")
                .append(" VALUES (:country, :city, :street, :zip, :latitude, :longitude, :menu_id, :company_id)")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        Location location = venue.getLocation();
        params.addValue("country", location.getCountry());
        params.addValue("city", location.getCity());
        params.addValue("street", location.getStreet());
        params.addValue("zip", location.getZip());
        params.addValue("latitude", location.getLatitude());
        params.addValue("longitude", location.getLongitude());
        params.addValue("company_id", venue.getCompany().getId());
        Long menuId = venue.getMenu() != null ? venue.getMenu().getId() : null;
        params.addValue("menu_id", menuId);

        return insertAndGetId(sql, params);
    }

    @Override
    public Venue get(int id) {
        String sql = new StringBuilder()
                .append(" SELECT ")
                .append(" v.id, v.country, v.city, v.street, v.zip, v.latitude, v.longitude,")
                .append(" m.id, m.name, m.company_id,")
                .append(" g.id, g.name, g.image,")
                .append(" gtm.group_id, gtm.tag_id,")
                .append(" GROUP_CONCAT(gt.name) AS group_tags,")
                .append(" p.id, p.name, p.image, p.price, p.group_id, p.description,")
                .append(" GROUP_CONCAT(pt.name) AS product_tags,")
                .append(" c.id, c.name, c.email, c.phone")
                .append(" FROM venues AS v")
                .append(" LEFT JOIN menus AS m ON(v.menu_id = m.id)")
                .append(" LEFT JOIN groups AS g ON(g.menu_id = m.id)")
                .append(" LEFT JOIN group_tag_map AS gtm ON (g.id = gtm.group_id)")
                .append(" LEFT JOIN tags AS gt ON (gt.id = gtm.tag_id)")
                .append(" INNER JOIN products AS p ON (p.group_id = g.id)")
                .append(" LEFT JOIN product_tag_map AS ptm ON (p.id = ptm.product_id)")
                .append(" LEFT JOIN tags AS pt ON (pt.id = ptm.tag_id)")
                .append(" LEFT JOIN companies AS c ON (c.id = m.company_id)")
                .append(" WHERE v.id = '" + id + "'")
                .append(" GROUP BY g.id, p.id")
                .toString();

        MapSqlParameterSource params = new MapSqlParameterSource();
        Venue venue = (Venue) jdbcTemplateObject.queryForObject(sql, params, new VenueMapper());
        return venue;
    }

    private static class VenueMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getVenue(rs);
        }
    }

    static Venue getVenue(ResultSet rs) throws SQLException {
        Location location = new Location()
                .setCity(rs.getString("v.city"))
                .setCountry(rs.getString("v.country"))
                .setStreet(rs.getString("v.street"))
                .setLatitude(rs.getDouble("v.latitude"))
                .setLongitude(rs.getDouble("v.longitude"))
                .setZip(rs.getString("v.zip"));

        Venue venue = new Venue()
                .setMenu(MenuJDBCTemplate.getMenu(rs))
                .setLocation(location);
        return venue;
    }

}
