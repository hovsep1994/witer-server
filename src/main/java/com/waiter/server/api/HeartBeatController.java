package com.waiter.server.api;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.solr.core.repository.product.ProductSolrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 1/17/2016.
 */
@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController extends AuthenticationController {

    @Autowired
    private ProductSearchService productSearchService;

    @Autowired
    ProductSolrRepository productSolrRepository;

    @RequestMapping(method = RequestMethod.GET)
    public MenuKitResponseEntity<String> heartbeat() {
        return MenuKitResponseEntity.success2("ok");
    }

}
