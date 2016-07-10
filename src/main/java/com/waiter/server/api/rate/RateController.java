package com.waiter.server.api.rate;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.product.model.ProductModel;
import com.waiter.server.api.rate.model.RateRequest;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hovsep on 5/31/16.
 */
@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProductModel> addMenu(@RequestBody RateRequest rateRequest) {
        Product product = productService.setRateByCustomerToken(rateRequest.getId(), rateRequest.getToken(), rateRequest.getRating());
        ProductModel productModel = ProductModel.convert(product, rateRequest.getLanguage());
        return ResponseEntity.success(productModel);
    }

}
