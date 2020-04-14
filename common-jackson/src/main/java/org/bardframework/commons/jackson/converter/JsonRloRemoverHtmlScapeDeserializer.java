package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JsonRloRemoverHtmlScapeDeserializer extends JsonDeserializer<String> {

    private static final char RLO = '\u202E';
    private static final char EMPTY = '\u0000';

    public static String removeRlo(String value) {
        return value.replace(RLO, EMPTY);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String rawValue = parser.getValueAsString();
        if (null == rawValue) {
            return null;
        }
        return JsonRloRemoverHtmlScapeDeserializer.removeRlo(rawValue).replace(RLO, EMPTY);
    }
}