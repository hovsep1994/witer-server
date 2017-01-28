package com.waiter.server.externalclients.foursquare.communicator;

import com.waiter.server.externalclients.common.communicator.AbstractApiCommunicatorImpl;
import com.waiter.server.externalclients.common.http.rest.RestClient;
import com.waiter.server.externalclients.foursquare.model.FoursquareModel;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2/2/2016.
 */
@Service
public class FoursquareApiCommunicatorImpl extends AbstractApiCommunicatorImpl implements FoursquareApiCommunicator {

    private static final String host = "https://api.foursquare.com/v2";
    private static final String searchVenues = "/venues/search";

    @Autowired
    private RestClient restClient;

    public FoursquareModel searchVenues(String query, String city) {
        String url = new StringBuilder().append(host).append(searchVenues).toString();
        Map<String, String> params = newAuthParams();
        params.put("query", query);
        params.put("near", city);
        params.put("categoryId", "4d4b7105d754a06374d81259,4d4b7105d754a06376d81259");
        final ResponseEntity<String> result = restClient.execute(url, HttpMethod.GET, request -> {
        }, response -> {
            final String result1 = IOUtils.toString(response.getBody());
            return new ResponseEntity<>(result1, response.getStatusCode());
        }, params);
        final FoursquareModel venueModel = deserializeJson(result.getBody(), FoursquareModel.class);
        return venueModel;
    }

    private static Map<String, String> newAuthParams() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "D1VYE1MQ0XCP3VCJJNHT4YAMFDL2OES3DZYIDJJ34ZXWG53C");
        params.put("client_secret", "RM5UTH24OQBU1C2ZGTLMWLJQTKENAXCG0PUAQVKENWD3AEZZ");
        params.put("v", "20151020");
        return params;
    }
}
