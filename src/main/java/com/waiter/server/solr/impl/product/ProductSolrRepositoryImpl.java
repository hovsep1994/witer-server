package com.waiter.server.solr.impl.product;

import com.waiter.server.solr.core.repository.product.ProductSolrRepositoryCustom;
import com.waiter.server.solr.core.repository.product.model.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hovsep on 8/3/16.
 */
public class ProductSolrRepositoryImpl implements ProductSolrRepositoryCustom {

    @Resource
    private SolrTemplate solrTemplate;

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

}
