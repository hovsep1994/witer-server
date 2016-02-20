package com.waiter.server.externalclients.foursquare.communicator;

import com.waiter.server.externalclients.foursquare.model.FoursquareVenueModel;

/**
 * Created by Admin on 2/2/2016.
 */
public interface FoursquareApiCommunicator {

    FoursquareVenueModel searchVenues(String query, String city);

}
