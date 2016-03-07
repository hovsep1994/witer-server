package com.waiter.server.externalclients.yandex.communicator;

import com.waiter.server.externalclients.common.communicator.AbstractApiCommunicatorImpl;
import com.waiter.server.externalclients.common.http.rest.RestClient;
import com.waiter.server.externalclients.yandex.model.YandexDetectedLanguageModel;
import com.waiter.server.externalclients.yandex.model.YandexTranslateTextModel;
import com.waiter.server.services.language.Language;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 2/2/2016.
 */
@Service
public class YandexTranslateApiCommunicatorImpl extends AbstractApiCommunicatorImpl implements YandexTranslateApiCommunicator {

    //for help look her :)
    //https://tech.yandex.com/translate/doc/dg/reference/translate-docpage/

    private static final String host = "https://translate.yandex.net/api/v1.5/tr.json";
    private static final String API_KEY = "trnsl.1.1.20160307T164503Z.0d4a13f2be0e97d9.e683153974acdd52d3c34c7280bfa0e72cf62c46";

    @Autowired
    private RestClient restClient;

    public String translate(String text, String langFrom, String langTo) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(host + "/translate")
                .queryParam("key", API_KEY)
                .queryParam("text", text)
                .queryParam("lang", langFrom + "-" + langTo)
                .build();
        final ResponseEntity<String> result = restClient.execute(uriComponents.toUri(), HttpMethod.GET, request -> {
        }, response -> {
            final String result1 = IOUtils.toString(response.getBody());
            return new ResponseEntity<>(result1, response.getStatusCode());
        });
        final YandexTranslateTextModel textModel = deserializeJson(result.getBody(), YandexTranslateTextModel.class);
        return textModel.getTexts().get(0);
    }

    public String detectLanguage(String text) {
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
        return "en";
    }

}
