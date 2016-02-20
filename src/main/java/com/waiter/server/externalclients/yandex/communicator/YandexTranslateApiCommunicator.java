package com.waiter.server.externalclients.yandex.communicator;

import com.waiter.server.externalclients.yandex.model.YandexDetectedLanguageModel;
import com.waiter.server.externalclients.yandex.model.YandexTranslateTextModel;

/**
 * Created by Admin on 2/2/2016.
 */
public interface YandexTranslateApiCommunicator {

    YandexTranslateTextModel translate(String text, String langFrom, String langTo);

    YandexDetectedLanguageModel detectLanguage(String text);

}
