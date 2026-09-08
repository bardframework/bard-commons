package org.bardframework.commons.jackson.converter;


import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.utils.persian.LetterConverterUtility;


@Slf4j
public class ArabicToPersianCharacterSerializer extends ValueSerializer<String> {

    @Override
    public void serialize(String string, JsonGenerator jsonGenerator, SerializationContext serializerProvider) {
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