package com.waiter.server.solr.impl.product;

import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.product.model.ProductInputDocument;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by hovsep on 8/3/16.
 */
@Component
public class ProductSolrRepositoryImpl implements ProductSolrRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSolrRepositoryImpl.class);
    private static final String PRODUCTS_COLLECTION = "products";

    @Resource
    private SolrTemplate solrTemplate;
    @Resource
    private SolrClient solrClient;

    @Override
    public List<ProductDocument> findBySearchParams(String text) {
        final SimpleQuery query = new SimpleQuery(new Criteria("locations_srpt").near(new Point(50.0, 50.0), new Distance(10, Metrics.KILOMETERS)));
        Page<ProductDocument> results = solrTemplate.queryForPage(query, ProductDocument.class);
        return results.getContent();
    }

    @Override
    public void save(ProductInputDocument product) throws IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", product.getId());
        document.addField("product_tags_ss", product.getProductTags(), (float) product.getRating());
        document.addField("category_tags_ss", product.getCategoryTags(), (float) product.getRating());
        document.addField("names_t", product.getNames(), (float) product.getRating());
        document.addField("descriptions_t", product.getDescriptions(), (float) product.getRating());
        document.addField("menu_id_s", product.getMenuId());

        for (SolrLocation location : product.getLocations()) {
            document.addField("locations_srpt",
                    location.getLatitude() + " " + location.getLongitude(), (float) product.getRating());
        }
        saveDoc(document);
    }

    @Override
    public void save(Collection<ProductInputDocument> docs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProductDocument findOne(String id) {
        throw new UnsupportedOperationException();
    }


    private void saveDoc(SolrInputDocument document) throws IOException {
        try {

            // todo
            // collect batch before sending doc
            // remove commit after enabling autocommit

            solrClient.add(PRODUCTS_COLLECTION, document);
            solrClient.commit(PRODUCTS_COLLECTION, true, true, true);
        } catch (SolrServerException e) {
            throw new IOException(e);
        }
    }

}
