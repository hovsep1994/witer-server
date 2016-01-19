package com.waiter.server.persistence.impl.venue;

import com.waiter.server.persistence.core.repository.venue.VenueRepositoryCustom;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Admin on 1/3/2016.
 */
@Component
public class VenueRepositoryImpl implements VenueRepositoryCustom {
    private static final Logger LOGGER = LoggerFactory.getLogger(VenueRepositoryImpl.class);

    private static final double DISTANCE = 10;
    private static final double DEG = 111;

    private static final String PARAMETER_LATITUDE = "latitude";
    private static final String PARAMETER_LONGITUDE = "longitude";
    private static final String PARAMETER_NAME = "name";


    @Autowired
    private EntityManager entityManager;

    public List<Venue> findBySearchParameters(VenueSearchParameters parameters) {
        Assert.notNull(parameters, "VenueSearchParameters must not be null");
        LOGGER.debug("Searching venues for parameters - {}", parameters);
        final TypedQuery<Venue> typedQuery = buildFindVenuesTypedQuery(parameters, Venue.class);

        List<Venue> venues = typedQuery.getResultList();
        return venues;
    }

    private <T> TypedQuery<T> buildFindVenuesTypedQuery(final VenueSearchParameters parameters,
                                                                 final Class<T> queryResultType) {
        final String queryString = buildFindVenuesQueryString(parameters);
        // Create typed query
        final TypedQuery<T> typedQuery = entityManager.createQuery(queryString, queryResultType);
        // Set parameters
        if (parameters.getName() != null && !parameters.getName().isEmpty()) {
            typedQuery.setParameter(PARAMETER_NAME, parameters.getName());
        }

        typedQuery.setParameter(PARAMETER_LATITUDE, parameters.getLatitude());
        typedQuery.setParameter(PARAMETER_LONGITUDE, parameters.getLongitude());

        return typedQuery;
    }

    private String buildFindVenuesQueryString(VenueSearchParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" SELECT v ");
        queryBuilder.append(" FROM Venue v ");
        queryBuilder.append(" INNER JOIN v.company ");
        queryBuilder.append(" LEFT JOIN v.location ");

        queryBuilder.append(" WHERE ");
        queryBuilder.append(" ABS(v.location.latitude - " + parameters.getLatitude() + ") < '" + DISTANCE / DEG + "'");
        queryBuilder.append(" AND ABS(v.location.longitude - " + parameters.getLongitude() + ") < '" + DISTANCE / (Math.cos(Math.toRadians(parameters.getLatitude())) * DEG) + "'");

        if (parameters.getName() != null && !parameters.getName().isEmpty()) {
            queryBuilder.append(" AND MATCH(c.name) AGAINST(:" + PARAMETER_NAME + " IN BOOLEAN MODE) ");
        }

        queryBuilder.append(" ORDER BY SQRT(POW(v.latitude - :" + PARAMETER_LATITUDE + ",2) + POW(v.longitude - " + PARAMETER_LONGITUDE + ", 2) )");

        return queryBuilder.toString();
    }

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

}
