package com.waiter.server.repository.sql;

import com.waiter.server.commons.entities.City;
import com.waiter.server.commons.entities.Country;
import com.waiter.server.repository.LocationsDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Repository
public class LocationsRepository extends BaseRepository implements LocationsDAO {

    @Override
    public List<Country> getAllCountries() {
        String sql = "SELECT * FROM countries ";
        return jdbcTemplateObject.query(sql, new MapSqlParameterSource(), new BeanPropertyRowMapper(Country.class));
    }

    public List<City> searchCities(String nameQuery, String countryCode, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder()
                .append("SELECT name FROM cities ")
                .append("WHERE country_code=:country_code ");

        params.addValue("country_code", countryCode);

        if (nameQuery != null && !nameQuery.isEmpty()) {
            sql.append("AND name LIKE :query ");
            params.addValue("query", nameQuery + "%");
        }
        sql.append("ORDER BY population DESC ");
        if (limit > 0) {
            sql.append("LIMIT :limit");
        }
        params.addValue("limit", limit);
        return jdbcTemplateObject.query(sql.toString(), params, new CityMapper());

    }

}
