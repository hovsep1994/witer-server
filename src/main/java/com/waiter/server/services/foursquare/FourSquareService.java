package com.waiter.server.services.foursquare;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by Admin on 1/17/2016.
 */
public interface FourSquareService {

    JsonNode searchVenues(String query, String city);

}

