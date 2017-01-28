package com.waiter.server.solr.core.repository.product.model;

import com.waiter.server.solr.core.repository.common.model.AbstractSolrDocumentWithId;
import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;
import java.util.Set;

/**
 * Created by hovsep on 7/31/16.
 */
@SolrDocument(solrCoreName = "products")
public class ProductDocument extends AbstractSolrDocumentWithId {

}
