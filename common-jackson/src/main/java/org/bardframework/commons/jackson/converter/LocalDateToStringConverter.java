package org.bardframework.commons.jackson.converter;

import org.bardframework.jalalidate.JalaliDate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
@Component
public class LocalDateToStringConverter extends ConverterSerializer<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyy/MM/dd");

    @Override
    public String convert(LocalDate value) {
        return JalaliDate.of(value).format(FORMATTER);
    }
}
