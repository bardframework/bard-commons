package org.bardframework.commons.jackson.converter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToByteConverter extends ConverterSerializer<String, Byte> {

    @Override
    public Byte convert(String value) {
        return Byte.valueOf(value);
    }
}
