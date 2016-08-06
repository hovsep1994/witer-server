package com.waiter.server.solr.impl.venue;

import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import com.waiter.server.solr.core.repository.venue.VenueSolrRepositoryCustom;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.util.List;

/**
 * Created by hovsep on 8/6/16.
 */
public class VenueSolrRepositoryImpl implements VenueSolrRepositoryCustom {

    private static final double DISTANCE = 10;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public List<VenueSolrDocument> findBySearchParameters(String name, Point point) {
        final Distance distance = new Distance(DISTANCE, Metrics.KILOMETERS);
        final Criteria criteria = new Criteria("location").near(point, distance)
                .or(new Criteria("name").isNotNull().startsWith(name));
        final SimpleQuery query = new SimpleQuery(criteria);
        final Page<VenueSolrDocument> results = solrTemplate.queryForPage(query, VenueSolrDocument.class);
        return results.getContent();
    }

}
