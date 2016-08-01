package com.waiter.server.solr.core.repository.venue;

import com.waiter.server.solr.core.repository.venue.model.VenueSolrDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Created by hovsep on 7/31/16.
 */
public interface VenueSolrRepository extends SolrCrudRepository<VenueSolrDocument, String> {
}
