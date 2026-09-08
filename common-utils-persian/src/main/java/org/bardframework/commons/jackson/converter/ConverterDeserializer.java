package org.bardframework.commons.jackson.converter;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;


/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public abstract class ConverterDeserializer<INPUT, OUTPUT> extends ValueDeserializer<OUTPUT> {

    public abstract OUTPUT convert(INPUT value);

    @Override
    public OUTPUT deserialize(JsonParser parser, DeserializationContext context) {
        INPUT value = (INPUT) parser.getValueAsString();
        if (null == value) {
            return null;
        }
        return convert(value);
    }
}
