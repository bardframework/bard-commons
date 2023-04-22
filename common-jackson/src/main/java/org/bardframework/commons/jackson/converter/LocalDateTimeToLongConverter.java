package org.bardframework.commons.jackson.converter;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 */
@Slf4j
public class LocalDateTimeToLongConverter extends JsonSerializer<LocalDateTime> implements Converter<LocalDateTime, Long> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            jsonGenerator.writeNumber(this.convert(dateTime));
        } catch (Exception e) {
            log.error("error when toModel local date time '{}' to mills", dateTime, e);
            jsonGenerator.writeNull();
        }
    }

    @Override
    public Long convert(LocalDateTime value) {
        return value.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}