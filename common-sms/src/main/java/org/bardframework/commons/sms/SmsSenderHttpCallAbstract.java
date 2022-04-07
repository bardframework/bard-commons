package org.bardframework.commons.sms;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.web.http.HttpCallResult;
import org.bardframework.commons.web.http.HttpCallerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class SmsSenderHttpCallAbstract implements SmsSender {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderHttpCallAbstract.class);

    @Override
    public SendResult send(String receiverNumber, String message) {
        try {
            return this.send(receiverNumber, message, null, null, null);
        } catch (Exception e) {
            LOGGER.error("error occurred in sms sender thread", e);
            return SendResult.ERROR;
        }
    }

    @Override
    public SendResult send(String receiverNumber, String message, String token) {
        try {
            return this.send(receiverNumber, message, token, null, null);
        } catch (Exception e) {
            LOGGER.error("error occurred in sms sender thread", e);
            return SendResult.ERROR;
        }
    }

    @Override
    public SendResult send(String receiverNumber, String message, String username, String password) {
        try {
            return this.send(receiverNumber, message, null, username, password);
        } catch (Exception e) {
            LOGGER.error("error occurred in sms sender thread", e);
            return SendResult.ERROR;
        }
    }

    private SendResult send(String receiverNumber, String message, String token, String username, String password) {
        String receiverNumberForLog = StringUtils.overlay(receiverNumber, "*", 4, receiverNumber.length() - 4);
        LOGGER.info("sending sms to:  " + receiverNumberForLog);
        Map<String, String> variables = new HashMap<>();
        variables.put("::to::", receiverNumber);
        variables.put("::message::", message);
        variables.put("::token::", token);
        variables.put("::username::", username);
        variables.put("::password::", password);

        try {
            HttpCallResult callResult = HttpCallerUtils.httpCall(this.getUrlTemplate(), this.getHttpMethod(), this.getBodyTemplate(), variables, this.getHeaders());
            LOGGER.info("Result of sending sms to [{}] is [{}]", receiverNumberForLog, callResult.getResponseCode());
            if (callResult.hasError()) {
                LOGGER.info("error response sending sms to [{}], [{}]", receiverNumberForLog, callResult.getBody());
                return SendResult.ERROR;
            }
            String body = new String(callResult.getBody(), StandardCharsets.UTF_8);
            if (null != this.getInsufficientCreditPattern() && Pattern.matches(this.getInsufficientCreditPattern(), body)) {
                return SendResult.INSUFFICIENT_CREDIT;
            }
            if (null != this.getSuccessPattern()) {
                if (Pattern.matches(this.getSuccessPattern(), body)) {
                    return SendResult.SUCCESS;
                } else {
                    return SendResult.ERROR;
                }
            }
            return SendResult.SUCCESS;
        } catch (Exception e) {
            LOGGER.debug("error sending sms", e);
            return SendResult.ERROR;
        }
    }

    public abstract String getHttpMethod();

    public abstract String getUrlTemplate();

    public abstract String getBodyTemplate();

    public abstract Map<String, String> getHeaders();

    public abstract String getSuccessPattern();

    public abstract String getInsufficientCreditPattern();
}
