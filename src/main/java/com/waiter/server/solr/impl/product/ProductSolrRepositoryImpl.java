package com.waiter.server.solr.impl.product;

import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepositoryCustom;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by hovsep on 8/3/16.
 */
public class ProductSolrRepositoryImpl implements ProductSolrRepositoryCustom {

    @Resource
    private SolrTemplate solrTemplate;
    @Resource
    private SolrClient solrClient;

    @Override
    public List<ProductDocument> findBySearchParams(String text) {
        final Criteria criteria =
                new Criteria("productName").contains(text).boost(1f)
                        .or(new Criteria("productTags").isNotNull().contains(text)).boost(2f)
                        .or(new Criteria("categoryName").contains(text)).boost(3f)
                        .or(new Criteria("categoryTags").isNotNull().contains(text)).boost(4f);
        final SimpleQuery query = new SimpleQuery(criteria);
        Page<ProductDocument> results = solrTemplate.queryForPage(query, ProductDocument.class);
        return results.getContent();
    }

    @Override
    public void save(ProductDocument product) throws IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("product_tags", product.getProductTags());
        document.addField("category_tags", product.getCategoryTags());
        for (SolrLocation location : product.getLocations()) {
            document.addField("locations_srpt", location.getLatitude() + " " + location.getLongitude());
        }
        try {
            solrClient.add(document);
        } catch (SolrServerException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void save(Collection<ProductDocument> docs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProductDocument findOne(String id) {
        throw new UnsupportedOperationException();
    }

}
