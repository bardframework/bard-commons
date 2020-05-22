package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public abstract class ConverterDeserializer<INPUT, OUTPUT> extends JsonDeserializer<OUTPUT> {

    public abstract OUTPUT convert(INPUT value);

    @Override
    public OUTPUT deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        INPUT value = (INPUT) parser.getValueAsString();
        if (null == value) {
            return null;
        }
        return convert(value);
    }
}
