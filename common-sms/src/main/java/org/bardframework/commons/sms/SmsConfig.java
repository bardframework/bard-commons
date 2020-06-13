package org.bardframework.commons.sms;

import org.bardframework.commons.config.ConfigKey;

public enum SmsConfig implements ConfigKey {
    SMS_SENDER_HTTP_METHOD("smsSender.httpMethod"),
    SMS_SENDER_URL_TEMPLATE("smsSender.url"),
    SMS_SENDER_SUCCESS_PATTERN("smsSender.successPattern", false),
    SMS_SENDER_INSUFFICIENT_CREDIT_PATTERN("smsSender.insufficientCreditPattern", false),
    SMS_SENDER_CONTENT_TYPE("smsSender.contentType", false),
    SMS_SENDER_BODY_TEMPLATE("smsSender.body", false),
    ;
    private final String key;
    private final Object defaultValue;
    private final boolean required;

    SmsConfig(String key, Object defaultValue, boolean required) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.required = required;
    }

    SmsConfig(String key, boolean required) {
        this(key, null, required);
    }

    SmsConfig(String key) {
        this(key, true);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
