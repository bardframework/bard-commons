package org.bardframework.commons.waf.exception;

import lombok.Getter;

@Getter
public class CallLimitExceedException extends Exception {

    private final String key;

    public CallLimitExceedException(String url) {
        this.key = url;
    }

}
