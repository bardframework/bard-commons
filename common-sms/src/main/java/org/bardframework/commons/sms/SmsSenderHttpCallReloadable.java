package org.bardframework.commons.sms;

import org.bardframework.commons.config.ReloadableConfig;

public class SmsSenderHttpCallReloadable extends SmsSenderHttpCallAbstract {

    @Override
    public String getHttpMethod() {
        return ReloadableConfig.get(SmsConfig.SMS_SENDER_HTTP_METHOD);
    }

    @Override
    public String getUrlTemplate() {
        return ReloadableConfig.get(SmsConfig.SMS_SENDER_URL_TEMPLATE);
    }

    @Override
    public String getSuccessPattern() {
        return ReloadableConfig.get(SmsConfig.SMS_SENDER_SUCCESS_PATTERN, null);
    }

    @Override
    public String getInsufficientCreditPattern() {
        return ReloadableConfig.get(SmsConfig.SMS_SENDER_INSUFFICIENT_CREDIT_PATTERN, null);
    }

    @Override
    public String getContentType() {
        return ReloadableConfig.get(SmsConfig.SMS_SENDER_CONTENT_TYPE, null);
    }

    @Override
    public String getBodyTemplate() {
        return ReloadableConfig.get(SmsConfig.SMS_SENDER_BODY_TEMPLATE, null);
    }
}
