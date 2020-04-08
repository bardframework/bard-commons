package org.bardframework.commons.jackson.converter;

import org.bardframework.time.JalaliDateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
@Component
public class JalaliDateTimeStringToLocalDateTimeConverter extends ConverterDeserializer<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String value) {
        return StringUtils.hasText(value) ? JalaliDateTime.of(value).toLocalDateTime() : null;
    }
}
