package com.waiter.server.services.yandex.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.yandex.TranslatorService;
import com.waiter.server.utils.requests.RequestSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 1/17/2016.
 */
@Service
public class TranslatorServiceImpl implements TranslatorService {

    //for help look her :)
    //https://tech.yandex.com/translate/doc/dg/reference/translate-docpage/

    private static final String host = "https://translate.yandex.net/api/v1.5/tr.json";
    private static final String API_KEY = "trnsl.1.1.20151120T084047Z.fddba949a73d76c0.1595a8906f956a5dce03a06c95adad334ab195d9";

    private RequestSender sender = new RequestSender();

    public JsonNode translate(String text, String langFrom, String langTo) {
        String url = new StringBuilder(host).append("/translate").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        params.put("lang", langFrom + "-" + langTo);
        try {
            JsonNode jsonNode = sender.getRequest(url, params);
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceRuntimeException(e);
        }
    }

    public JsonNode detectLanguage(String text) {
        String url = new StringBuilder(host).append("/detect").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        try {
            JsonNode jsonNode = sender.getRequest(url, params);
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceRuntimeException(e);
        }
    }

}
