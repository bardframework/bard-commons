package org.bardframework.commons.sms;

public class SmsSenderHttpCall extends SmsSenderHttpCallAbstract {

    protected final String httpMethod;
    protected final String urlTemplate;
    protected final String successPattern;
    protected final String insufficientCreditPattern;
    protected String contentType;
    protected String bodyTemplate;

    public SmsSenderHttpCall(String httpMethod, String urlTemplate, String successPattern, String insufficientCreditPattern) {
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
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }
}
