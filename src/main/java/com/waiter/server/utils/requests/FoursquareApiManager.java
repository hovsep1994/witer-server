package com.waiter.server.utils.requests;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shahenpoghosyan
 */
public class FoursquareApiManager {

    private static final String host = "https://api.foursquare.com/v2";
    private static final String searchVenues = "/venues/search";

    private RequestSender sender = new RequestSender();

    public JsonNode searchVenues(String query, String city) throws IOException {
        String url = new StringBuilder().append(host).append(searchVenues).toString();
        Map<String, String> params = newAuthParams();
        params.put("query", query);
        params.put("near", city);
        params.put("categoryId", "4d4b7105d754a06374d81259,4d4b7105d754a06376d81259");
        return sender.getRequest(url, params);
    }

    private static Map<String, String> newAuthParams() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "D1VYE1MQ0XCP3VCJJNHT4YAMFDL2OES3DZYIDJJ34ZXWG53C");
        params.put("client_secret", "RM5UTH24OQBU1C2ZGTLMWLJQTKENAXCG0PUAQVKENWD3AEZZ");
        params.put("v", "20151020");
        return params;
    }
}
