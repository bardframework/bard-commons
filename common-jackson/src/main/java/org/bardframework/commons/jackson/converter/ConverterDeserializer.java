package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

/**
 * Created by Vahid Zafari on 1/29/2016.
 * for enable spring ioc, it is necessary to call super()
 */
public abstract class ConverterDeserializer<INPUT, OUTPUT> extends JsonDeserializer<OUTPUT> implements Converter<INPUT, OUTPUT> {

    @Autowired
    protected MessageSource messageSource;

    public ConverterDeserializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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
