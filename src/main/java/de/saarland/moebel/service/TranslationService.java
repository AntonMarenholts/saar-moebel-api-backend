package de.saarland.moebel.service;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final Translator translator;

    public TranslationService(@Value("${DEEPL_AUTH_KEY}") String deeplAuthKey) {
        this.translator = new Translator(deeplAuthKey);
    }

    public String translate(String text, String targetLang) {
        if (text == null || text.isBlank()) {
            return text;
        }
        try {
            TextResult result = translator.translateText(text, null, targetLang);
            return result.getText();
        } catch (DeepLException | InterruptedException e) {
            // В реальном приложении здесь лучше логировать ошибку
            e.printStackTrace();
            // Возвращаем исходный текст в случае ошибки перевода
            return "Translation Error: " + text;
        }
    }
}