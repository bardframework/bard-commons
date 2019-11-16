package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bardframework.commons.model.BaseData;

import java.io.IOException;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringJsonToBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        BaseData baseData = parser.readValuesAs(BaseData.class).next();
        return null == baseData ? Boolean.FALSE : Boolean.valueOf(baseData.getId());
    }
}
