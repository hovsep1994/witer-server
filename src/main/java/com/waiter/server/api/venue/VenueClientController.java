package com.waiter.server.api.venue;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.search.model.response.VenueSearchModel;
import com.waiter.server.api.venue.model.response.VenueClientModel;
import com.waiter.server.api.venue.model.response.VenueClientNearbyModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.product.ProductService;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/client/venues")
public class VenueClientController extends MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueClientController.class);

    @Autowired
    private VenueService venueService;

    @Autowired
    private ProductService productService;

    @Value("#{appProperties['cdn.base.url']}")
    private String cdnBaseUrl;
    @Value("#{appProperties['api.base.url']}")
    private String baseUrl;


    @Autowired
    private VenueSearchService venueSearchService;

    @RequestMapping(value = "/nearby", method = RequestMethod.GET)
    public ResponseEntity nearby(@RequestParam Double latitude,
                                 @RequestParam Double longitude,
                                 @RequestParam(defaultValue = "0") int offset,
                                 @RequestParam(defaultValue = "20") int limit,
                                 @RequestParam Language language) {
        final VenueSearchParameters searchParameters = new VenueSearchParameters();
        searchParameters.setLatitude(latitude);
        searchParameters.setLongitude(longitude);
        searchParameters.setOffset(offset);
        searchParameters.setLimit(limit);

        final List<Venue> venues = venueSearchService.getVenuesBySearchParameters(searchParameters);
        final List<VenueClientNearbyModel> modelList = new ArrayList<>(venues.size());
        venues.forEach(venue -> modelList.add(VenueClientNearbyModel
                .convert(venue, language, productService.findTopProducts(venue.getMenu().getId(), 0, 10))));

        return MenuKitResponseEntity.success(modelList);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity findVenues(@RequestParam Double latitude,
                                     @RequestParam Double longitude,
                                     @RequestParam(required = false) String query,
                                     @RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "20") int limit) {

        final VenueSearchParameters searchParameters = new VenueSearchParameters();
        if (!StringUtils.isEmpty(query)) {
            searchParameters.setName(query);
        }
        searchParameters.setLatitude(latitude);
        searchParameters.setLongitude(longitude);
        searchParameters.setOffset(offset);
        searchParameters.setLimit(limit);

        final List<Venue> venues = venueSearchService.getVenuesBySearchParameters(searchParameters);
        final List<VenueSearchModel> modelList = venues.stream().map(VenueSearchModel::convert).collect(Collectors.toList());


        if (modelList.size() == limit) {
            Map<String, Object> map = new HashMap<>();
            map.put("query", query);
            map.put("offset", offset + limit);
            map.put("limit", limit);
            map.put("latitude", latitude);
            map.put("longitude", longitude);

            return MenuKitResponseEntity.success(modelList, baseUrl + "venues/search/", map);
        }
        return MenuKitResponseEntity.success(modelList);

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable Long id, @RequestParam Language language) {
        final Venue venue = venueService.getById(id);

        return MenuKitResponseEntity.success(VenueClientModel.convert(venue, language));
    }

}
