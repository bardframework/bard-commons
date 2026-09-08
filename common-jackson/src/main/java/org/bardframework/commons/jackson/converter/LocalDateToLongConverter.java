package org.bardframework.commons.jackson.converter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 **/
@Slf4j
public class LocalDateToLongConverter extends ValueSerializer<LocalDate> implements Converter<LocalDate, Long> {

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializationContext serializerProvider) {
        try {
            jsonGenerator.writeNumber(this.convert(date));
        } catch (Exception e) {
            log.error("error when toModel local date time '{}' to jalali date time string:", date, e);
            jsonGenerator.writeNull();
        }
    }

    @Override
    public Long convert(LocalDate source) {
        return source.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}