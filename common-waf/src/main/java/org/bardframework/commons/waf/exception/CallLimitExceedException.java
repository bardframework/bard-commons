package org.bardframework.commons.waf.exception;

public class CallLimitExceedException extends Exception {

    private final String key;

    public CallLimitExceedException(String url) {
        this.key = url;
    }

    public String getKey() {
        return key;
    }
}
