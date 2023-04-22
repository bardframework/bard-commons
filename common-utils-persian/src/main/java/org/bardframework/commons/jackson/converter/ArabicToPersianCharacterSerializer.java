package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.utils.persian.LetterConverterUtility;

import java.io.IOException;

@Slf4j
public class ArabicToPersianCharacterSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            if (string != null) {
                jsonGenerator.writeString(LetterConverterUtility.convertArabicCharacters(string));
                return;
            }
        } catch (Exception e) {
            log.error("error when normal '{}' to persian character", string, e);
        }
        jsonGenerator.writeNull();
    }
}