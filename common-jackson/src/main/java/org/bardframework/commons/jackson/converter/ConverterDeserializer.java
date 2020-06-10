package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public abstract class ConverterDeserializer<INPUT, OUTPUT> extends JsonDeserializer<OUTPUT> implements Converter<INPUT, OUTPUT> {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected MessageSource messageSource;

    public ConverterDeserializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public OUTPUT deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        if (null == parser.getValueAsString()) {
            return null;
        }
        INPUT value = (INPUT) parser.getValueAsString();
        return this.convert(value);
    }
}
