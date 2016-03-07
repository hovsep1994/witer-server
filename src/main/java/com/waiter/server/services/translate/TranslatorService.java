package com.waiter.server.services.translate;

import com.waiter.server.services.language.Language;
import com.waiter.server.services.translate.dto.TextTranslationDto;

/**
 * Created by Admin on 1/17/2016.
 */
public interface TranslatorService {

    String translate(TextTranslationDto textTranslationDto);

    Language detectLanguage(String text);
}
