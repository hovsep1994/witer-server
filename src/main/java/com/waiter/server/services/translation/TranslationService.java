package com.waiter.server.services.translation;

import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface TranslationService {

    Translation getById(Long id);

    Translation create(TranslationDto translationDto);

    Translation update(Long id, TranslationDto translationDto);

    List<Translation> create(List<Translation> translations);
}
