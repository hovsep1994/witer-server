package com.waiter.server.api.venue;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.location.model.LocationModel;
import com.waiter.server.api.menu.model.response.MenuWithProductsModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.api.venue.model.response.VenueClientResponseModel;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueSearchParameters;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/client/venue")
public class VenueClientController extends MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueClientController.class);

    @Autowired
    private VenueService venueService;

    @Value("#{appProperties['cdn.base.url']}")
    private String cdnBaseUrl;

    @Autowired
    private VenueSearchService venueSearchService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity findVenues(@RequestParam double latitude,
                                     @RequestParam double longitude,
                                     @RequestParam(required = false) String name,
                                     @RequestParam Language language) {
        final VenueSearchParameters searchParameters = new VenueSearchParameters();
        searchParameters.setName(name);
        searchParameters.setLatitude(latitude);
        searchParameters.setLongitude(longitude);
        final List<Venue> venues = venueSearchService.getVenuesBySearchParameters(searchParameters);
        final List<VenueClientResponseModel> modelList = new ArrayList<>(venues.size());
        venues.forEach(venue -> {
            final VenueClientResponseModel venueModel = new VenueClientResponseModel();
            venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.CATEGORY, venue.getGallery()));
            venueModel.setName(venue.getName());
            venueModel.setLocation(LocationModel.convert(venue.getLocation()));
            venueModel.setMenu(MenuWithProductsModel.convert(venue.getMenu(), language));
        });
        return MenuKitResponseEntity.success(modelList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable Long id, @RequestParam Language language) {
        final Venue venue = venueService.getById(id);
        final VenueClientResponseModel venueModel = new VenueClientResponseModel();
        venueModel.setImage(ImageUrlGenerator.getUrl(EntityType.CATEGORY, venue.getGallery()));
        venueModel.setName(venue.getName());
        venueModel.setLocation(LocationModel.convert(venue.getLocation()));
        venueModel.setMenu(MenuWithProductsModel.convert(venue.getMenu(), language));
        return MenuKitResponseEntity.success(venueModel);
    }

}
