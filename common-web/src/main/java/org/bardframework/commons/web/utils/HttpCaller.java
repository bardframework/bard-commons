package org.bardframework.commons.web.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpCaller {

    protected final String httpMethod;
    protected final String urlTemplate;
    protected String bodyTemplate;
    protected Map<String, String> headers = new HashMap<>();

    public HttpCaller(String httpMethod, String urlTemplate) {
        this.httpMethod = httpMethod;
        this.urlTemplate = urlTemplate;
    }

    public HttpCallResult call(Map<String, String> args) throws IOException {
        return this.call(this.getHeaders(), args);
    }

    /**
     * call with custom headers
     */
    public HttpCallResult call(Map<String, String> headers, Map<String, String> args) throws IOException {
        return HttpUtils.httpCall(this.getHttpMethod(), this.getUrlTemplate(), this.getBodyTemplate(), headers, args);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
