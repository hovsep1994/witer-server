package com.waiter.server.api.product;

import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.search.model.response.ProductSearchModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/client/products")
public class ProductClientController {

    @Autowired
    private ProductSearchService productSearchService;
    @Value("#{appProperties['api.base.url']}")
    private String baseUrl;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity findProducts(Double latitude, Double longitude,
                                       @RequestParam(required = false) String query,
                                       @RequestParam(defaultValue = "0") int offset,
                                       @RequestParam(defaultValue = "20") int limit,
                                       @RequestParam Language language) {
        final ProductSearchParameters parameters = new ProductSearchParameters();
        if (!StringUtils.isEmpty(query)) {
            parameters.setName(query);
        }
        parameters.setLatitude(latitude);
        parameters.setLongitude(longitude);
        parameters.setOffset(offset);
        parameters.setLimit(limit);

        final List<Product> products = productSearchService.findProducts(parameters);
        final List<ProductSearchModel> modelList = ProductSearchModel.convertToSearchModel(products, language);

        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        map.put("offset", offset + limit);
        map.put("limit", limit);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("language", language);

        return MenuKitResponseEntity.success(modelList,  baseUrl + "client/products/", map);
    }

}
