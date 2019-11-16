package org.bardframework.commons.web.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * Created by zafari on 4/12/2015.
 */
public abstract class BaseRestController<U> {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MessageSource messageSource;

    protected abstract U getUser();
}
