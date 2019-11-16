package org.bardframework.commons.exception;

import org.bardframework.commons.util.UtilityMethods;

import java.util.Arrays;

/**
 * Created by zafari on 4/11/2015
 */
public class NotExistException extends RuntimeException {

    private final String type;
    private final Object identifier;

    public NotExistException(Class<?> clazz, Object... identifiers) {
        this(clazz.getSimpleName(), identifiers);
    }

    public NotExistException(String type, Object... identifiers) {
        super("no " + type + " with " + Arrays.asList(identifiers) + " identifier(s) found.");
        this.type = UtilityMethods.toMessageKey(type);
        this.identifier = Arrays.deepToString(identifiers);
    }

    public Object getType() {
        return type;
    }

    public Object getIdentifier() {
        return identifier;
    }
}
