package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
public class LongToLocalDateTimeConverter extends JsonDeserializer<LocalDateTime> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return null == jsonParser.getValueAsString() ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getValueAsLong()), ZoneOffset.UTC);
        } catch (Exception e) {
            logger.info("error when converting StringLocalDateJalali '{}' to LocalDate.", jsonParser.getCurrentValue());
            logger.debug("exception details:", e);
            return null;
        }
    }
}
