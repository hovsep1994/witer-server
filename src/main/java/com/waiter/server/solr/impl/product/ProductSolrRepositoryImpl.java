package com.waiter.server.solr.impl.product;

import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.product.model.ProductInputDocument;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hovsep on 8/3/16.
 */
@Component
public class ProductSolrRepositoryImpl implements ProductSolrRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSolrRepositoryImpl.class);
    private static final String PRODUCTS_COLLECTION = "products";
    private static final String ID = "id";
    private static final String NAMES = "names_t";
    private static final String LOCATION = "locations_srpt";
    private static final String PRODUCT_TAGS = "product_tags_ss";
    private static final String CATEGORY_TAGS = "category_tags_ss";
    private static final String DESCRIPTIONS = "descriptions_t";
    private static final String MENUE_ID = "menu_id_s";

    @Resource
    private SolrTemplate productsSolrTemplate;
    @Resource
    private SolrClient solrClient;

    @Override
    public List<ProductDocument> findBySearchParams(String text, Point point, int offset, int limit)  {
        Map<String, String> map = new HashMap<>();
        if (text != null) {
            map.put("q", "+names_t:" + text + "*");
        } else {
            map.put("q", "*:*");
        }
        map.put("pt", point.getX() + "," + point.getY());
        map.put("sfield", LOCATION);
        map.put("fq", "{!geofilt}");
        map.put("d", "1000");
        map.put("wt", "json");
        try {
            QueryResponse queryResponse = solrClient.query(PRODUCTS_COLLECTION, new MapSolrParams(map));
            return queryResponse.getBeans(ProductDocument.class);
        } catch (Exception e) {
            throw new RuntimeException("Solr exception. ",  e);
        }
    }

    @Override
    public void save(ProductInputDocument product) throws IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField(ID, product.getId());
        document.addField(PRODUCT_TAGS, product.getProductTags(), (float) product.getRating());
        document.addField(CATEGORY_TAGS, product.getCategoryTags(), (float) product.getRating());
        document.addField(NAMES, product.getNames(), (float) product.getRating());
        document.addField(DESCRIPTIONS, product.getDescriptions(), (float) product.getRating());
        document.addField(MENUE_ID, product.getMenuId());

        for (SolrLocation location : product.getLocations()) {
            document.addField(LOCATION,
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
