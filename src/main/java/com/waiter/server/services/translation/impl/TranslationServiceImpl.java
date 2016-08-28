package com.waiter.server.services.translation.impl;

import com.waiter.server.persistence.core.repository.name.TranslationRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * @author shahenpoghosyan
 */
@Service
public class TranslationServiceImpl implements TranslationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationServiceImpl.class);

    @Autowired
    private TranslationRepository translationRepository;

    @Override
    public Translation getById(Long id) {
        notNull(id);
        Translation translation = translationRepository.findOne(id);
        if (translation == null) {
            LOGGER.error("Translation with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Translation not found");
        }
        return translation;
    }

    @Override
    public Translation create(TranslationDto translationDto) {
        assertTranslationDto(translationDto);
        Translation translation = new Translation();
        translationDto.updateProperties(translation);
        return translationRepository.save(translation);
    }

    @Override
    public Translation update(Long id, TranslationDto translationDto) {
        notNull(id);
        assertTranslationDto(translationDto);
        final Translation translation = getById(id);
        translationDto.updateProperties(translation);
        return translationRepository.save(translation);
    }

    @Override
    public List<Translation> create(List<Translation> translations) {
        return translationRepository.save(translations);
    }

    private void assertTranslationDto(TranslationDto translationDto) {
        notNull(translationDto, "translation dto must not be null");
        notNull(translationDto.getTranslationType(), "translation type must not be null");
        notNull(translationDto.getLanguage(), "language must not be null");
        notNull(translationDto.getText(), "text must not be null");
    }
}
