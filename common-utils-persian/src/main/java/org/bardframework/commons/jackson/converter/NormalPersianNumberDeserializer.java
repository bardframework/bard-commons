package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.utils.persian.LetterConverterUtility;

@Slf4j
public class NormalPersianNumberDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            if (jsonParser.getValueAsString() != null) {
                return LetterConverterUtility.convertFarsiNumbersToEnglish(jsonParser.getValueAsString());
            }
        } catch (Exception e) {
            log.error("error when normal '{}' to english number", jsonParser.getCurrentValue());
            log.debug("exception details:", e);
        }
        return "";

    }
}
