package org.bardframework.commons.jackson.converter;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.time.LocalDateJalali;

import java.time.LocalDate;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class JalaliDateStringToLocalDateConverter extends ConverterDeserializer<String, LocalDate> {

    @Override
    public LocalDate convert(String value) {
        return StringUtils.isNotBlank(value) ? LocalDateJalali.of(value).toLocalDate() : null;
    }
}
