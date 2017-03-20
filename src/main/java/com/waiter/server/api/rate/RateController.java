package com.waiter.server.api.rate;

import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.rate.model.RateRequest;
import com.waiter.server.api.rate.model.response.RateResponse;
import com.waiter.server.api.utility.rating.RoundRating;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hovsep on 5/31/16.
 */
@RestController
@RequestMapping("/client/rates")
public class RateController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.PUT)
    public ResponseEntity rateProduct(@PathVariable Long productId, @RequestBody RateRequest rateRequest) {
        final Product product = productService.setRateByCustomerToken(productId, rateRequest.getToken(), rateRequest.getRating());
        final RateResponse rateResponse = new RateResponse();
        rateResponse.setRate(RoundRating.round(product.getAverageRating()));
        return MenuKitResponseEntity.success(rateResponse);
    }

}
