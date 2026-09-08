package org.bardframework.commons.jackson.converter;


import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.utils.persian.LetterConverterUtility;

@Slf4j
public class NormalPersianNumberDeserializer extends ValueDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            if (jsonParser.getValueAsString() != null) {
                return LetterConverterUtility.convertFarsiNumbersToEnglish(jsonParser.getValueAsString());
            }
        } catch (Exception e) {
            log.error("error when normal '{}' to english number", jsonParser.currentValue());
            log.debug("exception details:", e);
        }
        return "";

    }
}
