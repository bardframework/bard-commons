package org.bardframework.commons.jackson.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class LocalDateToStringConverter extends ConverterSerializer<LocalDate, String> {

    private final DateTimeFormatter formatter;

    public LocalDateToStringConverter() {
        this.formatter = DateTimeFormatter.ofPattern("yyy/MM/dd");
    }

    public LocalDateToStringConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String convert(LocalDate value) {
        return value.format(formatter);
    }
}
