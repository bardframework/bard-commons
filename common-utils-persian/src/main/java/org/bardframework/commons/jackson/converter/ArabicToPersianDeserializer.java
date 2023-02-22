package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bardframework.commons.utils.persian.LetterConverterUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
public class ArabicToPersianDeserializer extends JsonDeserializer<String> {

    private static final Logger log = LoggerFactory.getLogger(ArabicToPersianDeserializer.class);

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
