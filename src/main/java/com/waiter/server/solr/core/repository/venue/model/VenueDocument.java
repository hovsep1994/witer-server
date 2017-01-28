package com.waiter.server.solr.core.repository.venue.model;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by hovsep on 7/31/16.
 */
@SolrDocument(solrCoreName = "venues")
public class VenueDocument extends AbstractSolrDocumentWithId {

}
