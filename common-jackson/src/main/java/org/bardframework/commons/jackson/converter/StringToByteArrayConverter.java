package org.bardframework.commons.jackson.converter;

import java.nio.charset.StandardCharsets;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToByteArrayConverter extends ConverterSerializer<String, byte[]> {

    @Override
    public byte[] convert(String value) {
        return null == value ? null : value.getBytes(StandardCharsets.UTF_8);
    }
}
