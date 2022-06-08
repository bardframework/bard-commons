package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Created by Vahid Zafari(v.zafari@chmail.ir) on 7/12/2016.
 **/
public class LocalDateToLongConverter extends JsonSerializer<LocalDate> implements Converter<LocalDate, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            jsonGenerator.writeNumber(this.convert(date));
        } catch (Exception e) {
            logger.error("error when toModel local date time '{}' to jalali date time string:", date, e);
            jsonGenerator.writeNull();
        }
    }

    @Override
    public Long convert(LocalDate source) {
        return source.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}