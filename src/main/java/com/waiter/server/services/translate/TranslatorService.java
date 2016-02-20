package com.waiter.server.services.translate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by Admin on 1/17/2016.
 */
public interface TranslatorService {

    String translate(String text, String langFrom, String langTo);

    String detectLanguage(String text);
}
