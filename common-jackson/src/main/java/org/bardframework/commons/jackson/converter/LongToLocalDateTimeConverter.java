package org.bardframework.commons.jackson.converter;


import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
@Slf4j
public class LongToLocalDateTimeConverter extends ValueDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return null == jsonParser.getValueAsString() ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getValueAsLong()), ZoneOffset.UTC);
        } catch (Exception e) {
            log.info("error when converting StringLocalDateJalali '{}' to LocalDate.", jsonParser.currentValue());
            log.debug("exception details:", e);
            return null;
        }
    }
}
