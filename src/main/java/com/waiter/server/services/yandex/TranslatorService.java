package com.waiter.server.services.yandex;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by Admin on 1/17/2016.
 */
public interface TranslatorService {

    JsonNode translate(String text, String langFrom, String langTo);

    JsonNode detectLanguage(String text);
}
