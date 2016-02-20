package com.waiter.server.services.translate.impl;

import com.waiter.server.externalclients.yandex.communicator.YandexTranslateApiCommunicator;
import com.waiter.server.services.translate.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 1/17/2016.
 */
@Service
public class TranslatorServiceImpl implements TranslatorService {

    @Autowired
    private YandexTranslateApiCommunicator yandexTranslateApiCommunicator;


    @Override
    public String translate(String text, String langFrom, String langTo) {
        yandexTranslateApiCommunicator.translate(text, langFrom, langTo);
        return null;
    }

    @Override
    public String detectLanguage(String text) {
        yandexTranslateApiCommunicator.detectLanguage(text);
        return null;
    }
}
