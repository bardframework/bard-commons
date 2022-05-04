package org.bardframework.commons.web.utils;

public class HttpCallResult {

    private final int responseCode;
    private final byte[] body;
    private final boolean error;

    public HttpCallResult(int responseCode, byte[] body, boolean error) {
        this.responseCode = responseCode;
        this.body = body;
        this.error = error;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public byte[] getBody() {
        return body;
    }

    public boolean hasError() {
        return error;
    }
}
