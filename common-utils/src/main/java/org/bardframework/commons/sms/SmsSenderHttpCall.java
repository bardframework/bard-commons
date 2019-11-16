package org.bardframework.commons.sms;

public class SmsSenderHttpCall extends SmsSenderHttpCallAbstract {

    protected final String httpMethod;
    protected final String url;
    protected final String successPattern;
    protected final String insufficientCreditPattern;
    protected String contentType;
    protected String body;

    public SmsSenderHttpCall(String httpMethod, String url, String successPattern, String insufficientCreditPattern) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.successPattern = successPattern;
        this.insufficientCreditPattern = insufficientCreditPattern;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getUrl() {
        return url;
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

    @Override
    public String getBody() {
        return body;
    }
}
