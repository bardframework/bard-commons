package org.bardframework.commons.jackson.converter;

import java.nio.charset.StandardCharsets;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class ByteArrayToStringConverter extends ConverterSerializer<byte[], String> {

    @Override
    public String convert(byte[] value) {
        return null == value ? null : new String(value, StandardCharsets.UTF_8);
    }
}
