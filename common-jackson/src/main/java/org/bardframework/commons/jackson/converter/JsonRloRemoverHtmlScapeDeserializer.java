package org.bardframework.commons.jackson.converter;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;


public class JsonRloRemoverHtmlScapeDeserializer extends ValueDeserializer<String> {

    private static final char RLO = '\u202E';
    private static final char EMPTY = '\u0000';

    public static String removeRlo(String value) {
        return value.replace(RLO, EMPTY);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) {
        String rawValue = parser.getValueAsString();
        if (null == rawValue) {
            return null;
        }
        return JsonRloRemoverHtmlScapeDeserializer.removeRlo(rawValue).replace(RLO, EMPTY);
    }
}