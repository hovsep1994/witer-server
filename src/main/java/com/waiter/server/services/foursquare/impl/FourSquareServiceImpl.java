package com.waiter.server.services.foursquare.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.foursquare.FourSquareService;
import com.waiter.server.utils.requests.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 1/17/2016.
 */
public class FourSquareServiceImpl implements FourSquareService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FourSquareServiceImpl.class);

    private static final String host = "https://api.foursquare.com/v2";
    private static final String searchVenues = "/venues/search";

    private RequestSender sender = new RequestSender();

    public JsonNode searchVenues(String query, String city) {
        String url = new StringBuilder().append(host).append(searchVenues).toString();
        Map<String, String> params = newAuthParams();
        params.put("query", query);
        params.put("near", city);
        params.put("categoryId", "4d4b7105d754a06374d81259,4d4b7105d754a06376d81259");
        try {
            JsonNode jsonNode = sender.getRequest(url, params);
            return jsonNode;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceRuntimeException(ErrorCode.IOException, e.getMessage());
        }
    }

    private static Map<String, String> newAuthParams() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "D1VYE1MQ0XCP3VCJJNHT4YAMFDL2OES3DZYIDJJ34ZXWG53C");
        params.put("client_secret", "RM5UTH24OQBU1C2ZGTLMWLJQTKENAXCG0PUAQVKENWD3AEZZ");
        params.put("v", "20151020");
        return params;
    }
}
