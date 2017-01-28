package com.waiter.server.externalclients.foursquare.communicator;

import com.waiter.server.externalclients.foursquare.model.FoursquareModel;

/**
 * Created by Admin on 2/2/2016.
 */
public interface FoursquareApiCommunicator {

    FoursquareModel searchVenues(String query, String city);

}
