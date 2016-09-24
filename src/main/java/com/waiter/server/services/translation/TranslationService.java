package com.waiter.server.services.translation;

import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.translation.model.TranslationType;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface TranslationService {

    Translation getById(Long id);

    Translation create(TranslationDto translationDto);

    Translation update(Long id, TranslationDto translationDto);

    Translation createOrUpdateTranslation(Long translationId, TranslationDto translationDto);

    List<Translation> create(List<Translation> translations);
}
