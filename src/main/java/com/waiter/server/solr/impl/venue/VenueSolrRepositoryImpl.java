package com.waiter.server.solr.impl.venue;

import com.waiter.server.solr.core.repository.venue.VenueSolrRepository;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * Created by hovsep on 8/6/16.
 */
@Repository
public class VenueSolrRepositoryImpl implements VenueSolrRepository {

    private static final double DISTANCE = 10;
    private static final String VENUES_COLLECTION = "venues";

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private SolrClient solrClient;

    @Override
    public void save(VenueSolrDocument venue) {
        final SolrInputDocument document = new SolrInputDocument();
        document.addField("id", venue.getId());
        document.addField("name", venue.getName());
        document.addField("location_rpt", venue.getLocation().getLatitude() + " " + venue.getLocation().getLongitude());
        document.addField("company_id", venue.getCompanyId());
        saveDocument(document);
    }

    @Override
    public VenueSolrDocument findOne(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VenueSolrDocument> findBySearchParameters(String name, Point point) {
        final Distance distance = new Distance(DISTANCE, Metrics.KILOMETERS);
        final Criteria criteria = new Criteria("location")
                .near(point, distance)
                .or(new Criteria("name").isNotNull().startsWith(name));
        final SimpleQuery query = new SimpleQuery(criteria);
        final Page<VenueSolrDocument> results = solrTemplate.queryForPage(query, VenueSolrDocument.class);
        return results.getContent();
    }

    private void saveDocument(SolrInputDocument document) {
        try {
            solrClient.add(VENUES_COLLECTION, document);
            solrClient.commit(VENUES_COLLECTION, true, true, true);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

}
