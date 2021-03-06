package com.waiter.server.solr.impl.product;

import com.waiter.server.solr.core.repository.common.model.SolrLocation;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import com.waiter.server.solr.core.repository.product.model.ProductInputDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

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
    private static final String MENU_ID = "menu_id_s";
    private static final String RATING = "rating_f";
    private static final float ZERO_BOOST = 0.0001f;

    @Resource
    private SolrTemplate productsSolrTemplate;
    @Resource
    private SolrClient solrClient;

    @Override
    public List<ProductDocument> findBySearchParams(String text, Point point, String sort, int offset, int limit)  {
        Map<String, String> map = new HashMap<>();
        if (text != null) {
            StringBuilder queryBuilder = new StringBuilder();
            Arrays.asList(text.split(" ")).stream().filter(s -> !s.isEmpty()).forEach(term ->
                    queryBuilder.append(" +names_t:").append(term).append("*"));
            map.put("q", queryBuilder.toString());
        } else {
            map.put("q", "*:*");
        }
        map.put("pt", point.getX() + "," + point.getY());
        map.put("sfield", LOCATION);
        map.put("fq", "{!geofilt}");
        map.put("d", "10000");
        map.put("wt", "json");
        map.put("rows", limit + "");
        map.put("start", offset + "");
        if (!StringUtils.isEmpty(sort)) {
            map.put("sort", sort);
        }

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
        document.addField(PRODUCT_TAGS, product.getProductTags(), getBoostFromRating(product.getRating()));
        document.addField(CATEGORY_TAGS, product.getCategoryTags(), getBoostFromRating(product.getRating()));
        document.addField(NAMES, product.getNames(), getBoostFromRating(product.getRating()));
        document.addField(DESCRIPTIONS, product.getDescriptions(), getBoostFromRating(product.getRating()));
        document.addField(MENU_ID, product.getMenuId());
        document.addField(RATING, product.getRating());

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

    private float getBoostFromRating(double rating) {
        return rating == 0 ? ZERO_BOOST : (float) rating;
    }

}
