package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bardframework.commons.utils.persian.LetterConverterUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormalPersianCharacterDeserializer extends JsonDeserializer<String> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            if (jsonParser.getValueAsString() != null) {
                return LetterConverterUtility.convertArabicCharacters(jsonParser.getValueAsString());
            }
        } catch (Exception e) {
            logger.error("error when normal '{}' to english number", jsonParser.getCurrentValue());
            logger.debug("exception details:", e);
        }
        return null;

    }
}
