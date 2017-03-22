package com.waiter.server.api.search;

import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.search.model.response.ProductSearchModel;
import com.waiter.server.api.search.model.response.SearchResponseModel;
import com.waiter.server.api.search.model.response.VenueSearchModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductSearchService;
import com.waiter.server.services.product.dto.ProductSearchParameters;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/client/search/")
public class SearchController {

    @Autowired
    private ProductSearchService productSearchService;
    @Autowired
    private VenueSearchService venueSearchService;
    @Value("#{appProperties['api.base.url']}")
    private String baseUrl;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity findVenues(@RequestParam(required = false) Double latitude,
                                     @RequestParam(required = false) Double longitude,
                                     @RequestParam(required = false) String query,
                                     @RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "20") int limit,
                                     @RequestParam(defaultValue = "0") int batch,
                                     @RequestParam(defaultValue = "true") boolean includeVenues,
                                     @RequestParam(defaultValue = "20") int venuesLimit,
                                     @RequestParam Language language) {
        final VenueSearchParameters venueParams = new VenueSearchParameters();
        final ProductSearchParameters productParams = new ProductSearchParameters();
        if (!StringUtils.isEmpty(query)) {
            venueParams.setName(query);
            productParams.setName(query);
        }
        if (longitude != null && latitude != null) {
            venueParams.setLatitude(latitude);
            venueParams.setLongitude(longitude);
            productParams.setLatitude(latitude);
            productParams.setLongitude(longitude);
        }
        venueParams.setOffset(0);
        venueParams.setLimit(venuesLimit);

        productParams.setOffset(batch * 100);
        productParams.setLimit(100);

        SearchResponseModel responseModel = new SearchResponseModel();
        List<Product> products = productSearchService.findProducts(productParams);
        if (includeVenues) {
            List<Venue> mainVenues = venueSearchService.getVenuesBySearchParameters(venueParams);
            if (mainVenues.size() < venuesLimit) {
                Map<Venue, Long> venues = products.stream().map(p -> p.getCategory().getMenu().getVenues().get(0))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                List<Venue> productVenues = venues.entrySet().stream().sorted((x, y) -> (int) (y.getValue() - x.getValue()))
                        .limit(venuesLimit).map(Map.Entry::getKey).collect(Collectors.toList());
                if (mainVenues.isEmpty()) {
                    mainVenues = productVenues;
                } else {
                    mainVenues.addAll(productVenues);
                }
                mainVenues.stream().distinct().limit(venuesLimit).collect(Collectors.toList());
            }
            responseModel.setVenues(VenueSearchModel.convert(mainVenues));
        }
        int productsOffset = offset - batch * 100;
        responseModel.setProducts(ProductSearchModel.convertToSearchModel(products.subList(productsOffset, productsOffset + limit > products.size()
                ? productsOffset + products.size() : productsOffset + limit), language));

        Map<String, Object> map = new HashMap<>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("query", query);
        map.put("offset", offset + limit);
        map.put("limit", limit);
        map.put("batch", offset / ((batch + 1) * 100));
        map.put("language", language);

        return MenuKitResponseEntity.success(responseModel, baseUrl + "search/", map);
    }


}
