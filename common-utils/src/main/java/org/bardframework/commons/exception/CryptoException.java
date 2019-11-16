package org.bardframework.commons.exception;

/**
 * Created by Vahid Zafari on 4/22/2016.
 */
public class CryptoException extends Exception {

    private static final long serialVersionUID = 1L;

    public CryptoException() {
    }

    public CryptoException(Throwable cause) {
        super(cause);
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
