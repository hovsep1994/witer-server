package com.waiter.server.solr.core.repository.venue;

import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
public interface VenueSolrRepositoryCustom {

    List<VenueSolrDocument> findBySearchParameters(String name, Point point);

}
