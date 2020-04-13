package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bardframework.commons.utils.persian.LetterConverterUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ArabicToPersianCharacterSerializer extends JsonSerializer<String> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            if (string != null) {
                jsonGenerator.writeString(LetterConverterUtility.convertArabicCharacters(string));
                return;
            }
        } catch (Exception e) {
            logger.error("error when normal '{}' to persian character", string, e);
        }
        jsonGenerator.writeNull();
    }
}