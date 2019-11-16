package org.bardframework.commons.exception;

import java.util.Arrays;

/**
 * Created by zafari on 4/11/2015 @rajaee
 */
public class ExistException extends RuntimeException {

    private final String type;
    private final Object identifier;

    public ExistException(String type, Object... identifiers) {
        super("instance of " + type + " with identifiers already exist: " + Arrays.deepToString(identifiers));
        this.type = type.trim();
        this.identifier = Arrays.deepToString(identifiers);
    }

    public Object getType() {
        return type;
    }

    public Object getIdentifier() {
        return identifier;
    }
}
