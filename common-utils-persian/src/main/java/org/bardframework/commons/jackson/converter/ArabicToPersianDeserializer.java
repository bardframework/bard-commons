package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.utils.persian.LetterConverterUtility;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
@Slf4j
public class ArabicToPersianDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return LetterConverterUtility.convertArabicCharacters(jsonParser.getValueAsString());
        } catch (Exception e) {
            log.info("error when converting StringLocalDateJalali '{}' to LocalDate.", jsonParser.getCurrentValue());
            log.debug("exception details:", e);
            return null;
        }
    }
}
