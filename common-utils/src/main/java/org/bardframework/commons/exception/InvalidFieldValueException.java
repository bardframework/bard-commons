package org.bardframework.commons.exception;

import org.bardframework.commons.util.AssertionUtils;

/**
 * Created by v.zafari on 1/26/2016.
 */
public class InvalidFieldValueException extends RuntimeException {

    private final String field;
    private final String invalidType;

    /**
     * Constructs a new runtime exception with given key as its error message key.
     * The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidFieldValueException(String field, String invalidType, Object value) {
        super("value of '" + field + "' " + invalidType + ": '" + value + "'");
        AssertionUtils.notNull(field, "null field not acceptable");
        AssertionUtils.notNull(invalidType, "null invalid type not acceptable");
        this.field = field;
        this.invalidType = invalidType;
    }

    public String getField() {
        return field;
    }

    public String getInvalidType() {
        return invalidType;
    }
}
