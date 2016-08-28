package com.waiter.server.api.name.model;

import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;

/**
 * Created by hovsep on 3/5/16.
 */
public class TranslationModel extends AbstractTranslationModel {


    public static TranslationModel convert(Translation translation) {
        TranslationModel nameTranslationModel = new TranslationModel();
        nameTranslationModel.setText(translation.getText());
        nameTranslationModel.setLanguage(translation.getLanguage());
        return nameTranslationModel;
    }

    public static TranslationDto convert(TranslationModel nameTranslationModel) {
        TranslationDto translationDto = new TranslationDto();
        translationDto.setLanguage(nameTranslationModel.getLanguage());
        translationDto.setText(nameTranslationModel.getText());
        return translationDto;
    }
}
