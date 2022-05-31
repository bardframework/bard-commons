package org.bardframework.commons.sms;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.web.utils.HttpCallResult;
import org.bardframework.commons.web.utils.HttpCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;

public class SmsSenderHttpCall extends HttpCaller implements SmsSender {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderHttpCall.class);

    protected final String successPattern;
    protected boolean disable;
    protected boolean logResponse;

    public SmsSenderHttpCall(String httpMethod, String urlTemplate, String successPattern) {
        super(httpMethod, urlTemplate);
        this.successPattern = successPattern;
    }

    @Override
    public final boolean send(Map<String, String> args) {
        if (!this.isDisable()) {
            LOGGER.error("sms sender is disable, can't send sms.");
            return false;
        }
        String receiverNumberForLog = StringUtils.overlay(args.get("to"), "*", 4, args.get("to").length() - 4);
        LOGGER.info("sending sms to:  " + receiverNumberForLog);
        try {
            HttpCallResult callResult = this.call(this.prepareHeadersForSend(), args);
            LOGGER.info("http status of sending sms to [{}] is [{}]", receiverNumberForLog, callResult.getResponseCode());
            if (this.isLogResponse()) {
                LOGGER.info("response sending sms to [{}], code: [{}], body: [{}]", receiverNumberForLog, callResult.getResponseCode(), callResult.getBody());
            }
            return this.isSuccess(callResult, receiverNumberForLog, args);
        } catch (Exception e) {
            LOGGER.error("error sending sms or processing response.", e);
            return false;
        }
    }

    protected boolean isSuccess(HttpCallResult result, String receiverNumberForLog, Map<String, String> args) throws Exception {
        if (result.hasError()) {
            return false;
        }
        String body = new String(result.getBody(), StandardCharsets.UTF_8);
        if (null != this.getSuccessPattern()) {
            return Pattern.matches(this.getSuccessPattern(), body);
        }
        return true;
    }

    protected Map<String, String> prepareHeadersForSend() throws Exception {
        return headers;
    }

    public String getSuccessPattern() {
        return successPattern;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isLogResponse() {
        return logResponse;
    }

    public void setLogResponse(boolean logResponse) {
        this.logResponse = logResponse;
    }
}
