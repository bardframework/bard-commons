package org.bardframework.commons.jackson.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class LocalDateTimeToStringConverter extends ConverterSerializer<LocalDateTime, String> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeToStringConverter() {
        this.formatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm");
    }

    public LocalDateTimeToStringConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String convert(LocalDateTime value) {
        return value.format(formatter);
    }
}
