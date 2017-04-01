package com.waiter.server.solr.impl.venue;

import com.waiter.server.solr.core.repository.venue.VenueSolrRepository;
import com.waiter.server.solr.core.repository.venue.model.VenueDocument;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
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

    private static final double DISTANCE = 10000;
    private static final String VENUES_COLLECTION = "venues";

    @Autowired
    private SolrTemplate venuesSolrTemplate;

    @Autowired
    private SolrClient solrClient;

    @Override
    public void save(VenueSolrDocument venue) {
        final SolrInputDocument document = new SolrInputDocument();
        document.addField("id", venue.getId());
        document.addField("name_txt", venue.getName());
        document.addField("location_rpt", venue.getLocation().getLongitude() + " " + venue.getLocation().getLatitude());
        document.addField("company_id_s", venue.getCompanyId());
        saveDocument(document);
    }

    @Override
    public VenueSolrDocument findOne(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VenueDocument> findBySearchParameters(String name, Point point, int offset, int limit) {
        final Distance distance = new Distance(DISTANCE, Metrics.KILOMETERS);
        Criteria criteria = null;
        if (!StringUtils.isEmpty(name)) {
            criteria = new Criteria("name_txt").startsWith(name);
        }
        if(point != null) {
            if(criteria == null) {
                criteria = new Criteria("location_rpt").near(point, distance);
            } else {
                criteria = criteria.and(new Criteria("location_rpt").near(point, distance));
            }
        }
        final SimpleQuery query = new SimpleQuery(criteria);
        query.setOffset(offset);
        query.setRows(limit);
        final Page<VenueDocument> results = venuesSolrTemplate.queryForPage(query, VenueDocument.class);
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
