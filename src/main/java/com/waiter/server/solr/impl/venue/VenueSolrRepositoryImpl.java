package com.waiter.server.solr.impl.venue;

import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.venue.VenueSolrRepository;
import com.waiter.server.solr.core.repository.venue.model.VenueDocument;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hovsep on 8/6/16.
 */
@Repository
public class VenueSolrRepositoryImpl implements VenueSolrRepository {

    private static final double DISTANCE = 10000;
    private static final String VENUES_COLLECTION = "venues";
    private static final float ZERO_BOOST = 0.0001f;

    @Autowired
    private SolrTemplate venuesSolrTemplate;

    @Autowired
    private SolrClient solrClient;

    @Override
    public void save(VenueSolrDocument venue) {
        final SolrInputDocument document = new SolrInputDocument();
        document.addField("id", venue.getId());
        document.addField("name_txt", venue.getName(), venue.getRating() == 0 ? ZERO_BOOST : (float) venue.getRating());
        document.addField("location_rpt", venue.getLocation().getLongitude() + " " + venue.getLocation().getLatitude());
        document.addField("company_id_s", venue.getCompanyId());
        saveDocument(document);
    }

    @Override
    public VenueSolrDocument findOne(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VenueDocument> findBySearchParameters(String name, Point point, String sort, int offset, int limit) {
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(name)) {
            StringBuilder queryBuilder = new StringBuilder();
            Arrays.asList(name.split(" ")).stream().filter(s -> !s.isEmpty()).forEach(term ->
                    queryBuilder.append(" +name_txt:").append(term).append("*"));
            map.put("q", queryBuilder.toString());
        } else {
            map.put("q", "*:*");
        }
        map.put("pt", point.getX() + "," + point.getY());
        map.put("sfield", "location_rpt");
        map.put("fq", "{!geofilt}");
        map.put("d", DISTANCE + "");
        map.put("wt", "json");
        map.put("rows", limit + "");
        map.put("start", offset + "");

        if (!StringUtils.isEmpty(sort)) {
            map.put("sort", sort);
        }
        try {
            QueryResponse queryResponse = solrClient.query(VENUES_COLLECTION, new MapSolrParams(map));
            return queryResponse.getBeans(VenueDocument.class);
        } catch (Exception e) {
            throw new RuntimeException("Solr exception. ",  e);
        }
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
