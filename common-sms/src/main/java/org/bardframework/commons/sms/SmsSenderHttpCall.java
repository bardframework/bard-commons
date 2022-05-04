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
    protected final String insufficientCreditPattern;

    public SmsSenderHttpCall(String httpMethod, String urlTemplate, String successPattern, String insufficientCreditPattern) {
        super(httpMethod, urlTemplate);
        this.successPattern = successPattern;
        this.insufficientCreditPattern = insufficientCreditPattern;
    }

    public String getSuccessPattern() {
        return successPattern;
    }

    public String getInsufficientCreditPattern() {
        return insufficientCreditPattern;
    }

    @Override
    public SendResult send(Map<String, String> args) {
        String receiverNumberForLog = StringUtils.overlay(args.get("to"), "*", 4, args.get("to").length() - 4);
        LOGGER.info("sending sms to:  " + receiverNumberForLog);
        try {
            HttpCallResult callResult = this.call(headers, args);
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
            LOGGER.error("error sending sms", e);
            return SendResult.ERROR;
        }
    }
}
