package com.waiter.server.persistence.impl.venue;

import com.waiter.server.persistence.core.repository.venue.VenueRepositoryCustom;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 1/3/2016.
 */
@Component
public class VenueRepositoryImpl implements VenueRepositoryCustom {

    private static final double DISTANCE = 10;
    private static final double DEG = 111;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Venue> findByName(String name, double lat, double lon) {

        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" v.id, v.country, v.city, v.street, v.zip, v.latitude, v.longitude, v.company_id,")
                .append(" c.id, c.name")
                .append(" FROM venues AS v")
                .append(" INNER JOIN companies AS c ON (v.company_id = c.id)")
                .append(" WHERE ABS(v.latitude - " + lat + ") < '" + DISTANCE / DEG + "'")
                .append(" AND ABS(v.longitude - " + lon + ") < '" + DISTANCE / (Math.cos(Math.toRadians(lat)) * DEG) + "'");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND MATCH(c.name) AGAINST(:name IN BOOLEAN MODE) ");
            params.addValue("name", name + "*");
        }

        sql.append(" ORDER BY SQRT(POW(v.latitude - " + lat + ",2) + POW(v.longitude - " + lon + ", 2) )");

//        List<Venue> venues = jdbcTemplate.query(sql.toString(), params, new VenueMapperWithoutMenu());
//        List<Venue> venues = jdbcTemplate.query(sql.toString(), new Object[]{name}, new VenueMapperWithoutMenu());

        Query query = entityManager.createNativeQuery(sql.toString(), Venue.class);
        List<Venue> venues = query.getResultList();

        return venues;
    }

    private static class VenueMapperWithoutMenu implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location()
                    .setCity(rs.getString("v.city"))
                    .setCountry(rs.getString("v.country"))
                    .setStreet(rs.getString("v.street"))
                    .setLatitude(rs.getDouble("v.latitude"))
                    .setLongitude(rs.getDouble("v.longitude"))
                    .setZip(rs.getString("v.zip"));

            Company company = new Company();
            company.setName(rs.getString("c.name"));

            Venue venue = new Venue();
            venue.setId(rs.getLong("v.id"));
            venue.setLocation(location);
            venue.setCompany(company);

            return venue;
        }
    }

    private void foo() {

    }

}
