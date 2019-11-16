package org.bardframework.commons.exception;

/**
 * Created by v.zafari on 29/11/2015.
 */
public class InUseDataDeleteException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final Object type;

    public InUseDataDeleteException(Object type, String message) {
        super(message);
        this.type = type;
    }

    public InUseDataDeleteException(Object type) {
        this(type, "instance of '" + type + "' is in use in other objects");
    }

    public Object getType() {
        return type;
    }
}