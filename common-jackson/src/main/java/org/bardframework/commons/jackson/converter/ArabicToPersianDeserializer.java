package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bardframework.commons.util.LetterConverterUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
@Component
public class ArabicToPersianDeserializer extends JsonDeserializer<String> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        try {
            return LetterConverterUtility.convertArabicCharacters(jsonParser.getValueAsString());
        } catch (Exception e) {
            logger.info("error when converting StringJalaliDate '{}' to LocalDate.", jsonParser.getCurrentValue());
            logger.debug("exception details:", e);
            return null;
        }
    }
}