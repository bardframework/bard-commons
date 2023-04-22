package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
@Slf4j
public class LongToLocalDateConverter extends JsonDeserializer<LocalDate> implements Converter<Long, LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return this.convert(jsonParser.getValueAsLong());
        } catch (Exception e) {
            log.info("error when converting StringLocalDateJalali '{}' to LocalDate.", jsonParser.getCurrentValue());
            log.debug("exception details:", e);
            return null;
        }
    }

    @Override
    public LocalDate convert(Long mills) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(mills), ZoneOffset.UTC).toLocalDate();
    }
}
