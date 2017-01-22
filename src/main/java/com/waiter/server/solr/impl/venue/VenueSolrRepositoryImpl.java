package com.waiter.server.solr.impl.venue;

import com.waiter.server.solr.core.repository.venue.VenueSolrRepository;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hovsep on 8/6/16.
 */
@Repository
public class VenueSolrRepositoryImpl implements VenueSolrRepository {

    private static final double DISTANCE = 10;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void save(VenueSolrDocument document) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VenueSolrDocument findOne(String id) {
        throw new UnsupportedOperationException();
    }

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
