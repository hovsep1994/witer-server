package com.waiter.server.utils.requests;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 11/20/2015.
 */
public class YandexTranslatorApiManager {

    //for help look her :)
    //https://tech.yandex.com/translate/doc/dg/reference/translate-docpage/

    private static final String host = "https://translate.yandex.net/api/v1.5/tr.json";
    private static final String API_KEY = "trnsl.1.1.20151120T084047Z.fddba949a73d76c0.1595a8906f956a5dce03a06c95adad334ab195d9";

    private RequestSender sender = new RequestSender();

    public JsonNode translate(String text, String langFrom, String langTo) throws IOException {
        String url = new StringBuilder(host).append("/translate").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        params.put("lang", langFrom + "-" + langTo);
        return sender.getRequest(url, params);
    }

    public JsonNode translate(String text, String langTo) throws IOException {
        String url = new StringBuilder(host).append("/translate").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        params.put("lang", langTo);
        return sender.getRequest(url, params);
    }

    public JsonNode detectLanguage(String text) throws IOException {
        String url = new StringBuilder(host).append("/detect").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        return sender.getRequest(url, params);
    }

}
