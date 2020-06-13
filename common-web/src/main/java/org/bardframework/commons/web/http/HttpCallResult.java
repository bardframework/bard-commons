package org.bardframework.commons.web.http;

public class HttpCallResult {

    private final int responseCode;
    private final String body;
    private final boolean error;

    public HttpCallResult(int responseCode, String body, boolean error) {
        this.responseCode = responseCode;
        this.body = body;
        this.error = error;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getBody() {
        return body;
    }

    public boolean hasError() {
        return error;
    }
}
