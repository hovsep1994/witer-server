package com.waiter.server.api.product;

import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/client/products")
public class ProductClientController {

    @Autowired
    private ProductSearchService productSearchService;

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
        final List<ProductModel> modelList = ProductModel.convert(products, language);

        return MenuKitResponseEntity.success(modelList);
    }

}
