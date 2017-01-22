package com.waiter.server.solr.core.repository.venue;

import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.springframework.data.solr.core.geo.Point;

import java.util.List;

/**
 * Created by hovsep on 7/31/16.
 */
public interface VenueSolrRepositoryCustom {


    void save(VenueSolrDocument document);

    VenueSolrDocument findOne(String id);

    List<VenueSolrDocument> findBySearchParameters(String name, Point point);


}
