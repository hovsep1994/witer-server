package com.waiter.server.services.identity.impl.foursquare;

import com.waiter.server.externalclients.foursquare.model.FoursquareLocationModel;
import com.waiter.server.externalclients.foursquare.model.FoursquareModel;
import com.waiter.server.persistence.core.repository.identity.foursquare.FoursquareIdentityRepository;
import com.waiter.server.services.identity.foursquare.FoursquareIdentityService;
import com.waiter.server.services.identity.model.foursquare.FoursquareIdentity;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.notNull;

/**
 * Created by hovsep on 5/15/16.
 */
@Service
public class FoursquareIdentityServiceImpl implements FoursquareIdentityService {

    @Autowired
    private FoursquareIdentityRepository foursquareIdentityRepository;

    @Autowired
    private VenueService venueService;

    @Override
    public FoursquareIdentity createVenueFoursquareIdentity(FoursquareModel foursquareModel, Long companyId) {
        notNull(foursquareModel);
        notNull(foursquareModel.getJson());
        FoursquareLocationModel locationModel = foursquareModel.getVenue().getLocation();
        Location location = new Location();
        location.setCity(locationModel.getCity());
        location.setCountry(locationModel.getCountry());
        location.setStreet(locationModel.getAddress());
        location.setLatitude(locationModel.getLat());
        location.setLongitude(locationModel.getLng());
        VenueDto venueDto = new VenueDto();
        venueDto.setName(foursquareModel.getVenue().getName());
        Venue venue = venueService.create(venueDto, location, companyId);
        FoursquareIdentity foursquareIdentity = new FoursquareIdentity();
        foursquareIdentity.setReferralId(foursquareModel.getReferralId());
        foursquareIdentity.setName(foursquareModel.getVenue().getName());
        foursquareIdentity.setRating(foursquareModel.getVenue().getRating());
        foursquareIdentity.setJson(foursquareModel.getJson());
        foursquareIdentity.setVenueId(venue.getId());
        return foursquareIdentityRepository.save(foursquareIdentity);
    }

}
