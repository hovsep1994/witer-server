package com.waiter.server.services.identity.foursquare;

import com.waiter.server.externalclients.foursquare.model.FoursquareModel;
import com.waiter.server.services.identity.model.foursquare.FoursquareIdentity;

/**
 * Created by hovsep on 5/14/16.
 */
public interface FoursquareIdentityService {

    FoursquareIdentity createVenueFoursquareIdentity(FoursquareModel foursquareModel, Long companyId);

}
