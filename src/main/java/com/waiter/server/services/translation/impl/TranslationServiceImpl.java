package com.waiter.server.services.translation.impl;

import com.waiter.server.persistence.core.repository.name.NameRepository;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private NameRepository nameRepository;

    @Override
    public Translation create(TranslationDto translationDto) {
        assertNameTranslationDto(translationDto);
        Translation translation = new Translation();
        translationDto.updateProperties(translation);
        return nameRepository.save(translation);
    }

    @Override
    public List<Translation> create(List<Translation> translations) {
        return nameRepository.save(translations);
    }

    private void assertNameTranslationDto(TranslationDto translationDto) {
        Assert.notNull(translationDto, "translation dto must not be null");
        Assert.notNull(translationDto.getTranslationType(), "translation type must not be null");
        Assert.notNull(translationDto.getLanguage(), "language must not be null");
        Assert.notNull(translationDto.getText(), "text must not be null");
    }
}
