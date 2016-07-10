package com.waiter.server.api.translate;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.translate.model.TranslateTextModel;
import com.waiter.server.services.translate.TranslatorService;
import com.waiter.server.services.translate.dto.TextTranslationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hovsep on 3/7/16.
 */
@RestController
@RequestMapping("/translate")
public class TranslateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateController.class);

    @Autowired
    private TranslatorService translatorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> translate(@RequestBody TranslateTextModel translateTextModel) {
        TextTranslationDto textTranslationDto = new TextTranslationDto();
        textTranslationDto.setText(translateTextModel.getText());
        textTranslationDto.setLanguageFrom(translateTextModel.getLanguageFrom());
        textTranslationDto.setLanguageTo(translateTextModel.getLanguageTo());
        String translatedText = translatorService.translate(textTranslationDto);
        return ResponseEntity.success(translatedText);
    }
}
