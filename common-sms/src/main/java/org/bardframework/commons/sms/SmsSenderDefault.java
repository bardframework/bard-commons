package org.bardframework.commons.sms;

import java.util.HashMap;
import java.util.Map;

public class SmsSenderDefault extends SmsSenderHttpCallAbstract {

    protected final String httpMethod;
    protected final String urlTemplate;
    protected final String successPattern;
    protected final String insufficientCreditPattern;
    protected Map<String, String> headers = new HashMap<>();
    protected String bodyTemplate;

    public SmsSenderDefault(String httpMethod, String urlTemplate, String successPattern, String insufficientCreditPattern) {
        this.httpMethod = httpMethod;
        this.urlTemplate = urlTemplate;
        this.successPattern = successPattern;
        this.insufficientCreditPattern = insufficientCreditPattern;
    }

    @Override
    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getUrlTemplate() {
        return urlTemplate;
    }

    @Override
    public String getSuccessPattern() {
        return successPattern;
    }

    @Override
    public String getInsufficientCreditPattern() {
        return insufficientCreditPattern;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }
}
