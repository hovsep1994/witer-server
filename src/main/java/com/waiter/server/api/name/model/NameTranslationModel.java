package com.waiter.server.api.name.model;

import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;

/**
 * Created by hovsep on 3/5/16.
 */
public class NameTranslationModel extends AbstractNameTranslationModel {


    public static NameTranslationModel convert(Translation translation) {
        NameTranslationModel nameTranslationModel = new NameTranslationModel();
        nameTranslationModel.setName(translation.getName());
        nameTranslationModel.setLanguage(translation.getLanguage());
        return nameTranslationModel;
    }

    public static TranslationDto convert(NameTranslationModel nameTranslationModel) {
        TranslationDto translationDto = new TranslationDto();
        translationDto.setLanguage(nameTranslationModel.getLanguage());
        translationDto.setName(nameTranslationModel.getName());
        return translationDto;
    }
}
