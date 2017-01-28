package com.waiter.server.externalclients.yandex.communicator;

import com.waiter.server.externalclients.yandex.model.YandexDetectedLanguageModel;
import com.waiter.server.externalclients.yandex.model.YandexTranslateTextModel;
import com.waiter.server.services.language.Language;

/**
 * Created by Admin on 2/2/2016.
 */
public interface YandexTranslateApiCommunicator {

    String translate(String text, String langFrom, String langTo);

    String detectLanguage(String text);

}
