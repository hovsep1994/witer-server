package com.waiter.server.externalclients.yandex.communicator;

import com.waiter.server.externalclients.common.communicator.AbstractApiCommunicatorImpl;
import com.waiter.server.externalclients.common.http.rest.RestClient;
import com.waiter.server.externalclients.yandex.model.YandexDetectedLanguageModel;
import com.waiter.server.externalclients.yandex.model.YandexTranslateTextModel;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 2/2/2016.
 */
@Service
public class YandexTranslateApiCommunicatorImpl extends AbstractApiCommunicatorImpl implements YandexTranslateApiCommunicator {

    //for help look her :)
    //https://tech.yandex.com/translate/doc/dg/reference/translate-docpage/

    private static final String host = "https://translate.translate.net/api/v1.5/tr.json";
    private static final String API_KEY = "trnsl.1.1.20151120T084047Z.fddba949a73d76c0.1595a8906f956a5dce03a06c95adad334ab195d9";

    @Autowired
    private RestClient restClient;

    public YandexTranslateTextModel translate(String text, String langFrom, String langTo) {
        String url = new StringBuilder(host).append("/translate").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        params.put("lang", langFrom + "-" + langTo);
        final ResponseEntity<String> result = restClient.execute(url, HttpMethod.GET, request -> {
        }, response -> {
            final String result1 = IOUtils.toString(response.getBody());
            return new ResponseEntity<>(result1, response.getStatusCode());
        }, params);
        final YandexTranslateTextModel textModel = deserializeJson(result.getBody(), YandexTranslateTextModel.class);
        return textModel;
    }

    public YandexDetectedLanguageModel detectLanguage(String text) {
        String url = new StringBuilder(host).append("/detect").toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", API_KEY);
        params.put("text", text);
        final ResponseEntity<String> result = restClient.execute(url, HttpMethod.GET, request -> {
        }, response -> {
            final String result1 = IOUtils.toString(response.getBody());
            return new ResponseEntity<>(result1, response.getStatusCode());
        }, params);
        final YandexDetectedLanguageModel textModel = deserializeJson(result.getBody(), YandexDetectedLanguageModel.class);
        return textModel;
    }

}
