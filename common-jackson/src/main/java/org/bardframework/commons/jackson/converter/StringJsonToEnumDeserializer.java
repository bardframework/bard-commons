package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.bardframework.commons.model.BaseData;

import java.io.IOException;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringJsonToEnumDeserializer<T extends Enum<T>> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<T> enumClazz;

    public StringJsonToEnumDeserializer() {
    }

    public StringJsonToEnumDeserializer(Class<T> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        return new StringJsonToEnumDeserializer(property.getType().getRawClass());
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        BaseData baseData = parser.readValuesAs(BaseData.class).next();
        return Enum.valueOf(enumClazz, baseData.getId());
    }
}
