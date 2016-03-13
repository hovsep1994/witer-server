package com.waiter.server.services.translate.impl;

import com.waiter.server.externalclients.yandex.communicator.YandexTranslateApiCommunicator;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translate.TranslatorService;
import com.waiter.server.services.translate.dto.TextTranslationDto;
import com.waiter.server.services.translate.dto.TextsTranslationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 1/17/2016.
 */
@Service
public class TranslatorServiceImpl implements TranslatorService {

    private static Logger LOGGER = LoggerFactory.getLogger(TranslatorServiceImpl.class);

    private static final String SPLITTER = "~";

    @Autowired
    private YandexTranslateApiCommunicator yandexTranslateApiCommunicator;

    @Override
    public String translate(TextTranslationDto textTranslationDto) {
        return yandexTranslateApiCommunicator.translate(
                textTranslationDto.getText(),
                textTranslationDto.getLanguageFrom().name(),
                textTranslationDto.getLanguageTo().name());
    }

    public List<String> translate(TextsTranslationDto textsTranslationDto) throws ServiceException {
        String textsString = yandexTranslateApiCommunicator.translate(
                convertToString(textsTranslationDto.getTexts()),
                textsTranslationDto.getLanguageFrom().name(),
                textsTranslationDto.getLanguageTo().name());
        List<String> texts = convertToList(textsString);
        if (texts.size() != textsTranslationDto.getTexts().size()) {
            LOGGER.error("request array size -{} is not equal to response array size -{}",
                    textsTranslationDto.getTexts().size(), texts.size());
            throw new ServiceException(ErrorCode.FAILD_TRANSLATION,
                    "request array size is not equal to response array size");
        }
        return texts;
    }

    @Override
    public Language detectLanguage(String text) {
        String languageString = yandexTranslateApiCommunicator.detectLanguage(text);
        return Language.valueOf(languageString);
    }

    private String convertToString(List<String> texts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String text : texts) {
            stringBuilder.append(text).append(SPLITTER);
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private List<String> convertToList(String textsString) {
        String[] textsArray = textsString.split(SPLITTER);
        return Arrays.asList(textsArray);
    }
}
