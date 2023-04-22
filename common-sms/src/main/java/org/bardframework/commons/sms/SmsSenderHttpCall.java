package org.bardframework.commons.sms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.web.http.HttpCallResult;
import org.bardframework.commons.web.http.HttpCaller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SmsSenderHttpCall extends HttpCaller implements SmsSender {
    protected final Pattern successPattern;

    public SmsSenderHttpCall(String httpMethod, String urlTemplate, String successPattern) {
        super(httpMethod, urlTemplate);
        this.successPattern = Pattern.compile(successPattern);
    }

    @Override
    public final boolean send(Map<String, String> args) throws IOException {
        String receiverNumberForLog = StringUtils.overlay(args.get("to"), "*", 4, args.get("to").length() - 4);
        log.info("try sending sms to: {}", receiverNumberForLog);
        HttpCallResult callResult = this.call(this.prepareHeadersForSend(), args);
        log.info("http status of sending sms to [{}] is [{}]", receiverNumberForLog, callResult.getResponseCode());
        return this.isSuccess(callResult, receiverNumberForLog, args);
    }

    protected boolean isSuccess(HttpCallResult result, String receiverNumberForLog, Map<String, String> args) throws IOException {
        if (result.hasError()) {
            return false;
        }
        String body = new String(result.getBody(), StandardCharsets.UTF_8);
        if (null != this.getSuccessPattern()) {
            Matcher matcher = this.getSuccessPattern().matcher(body);
            return matcher.matches() || matcher.find();
        }
        return true;
    }

    protected Map<String, String> prepareHeadersForSend() throws IOException {
        return headers;
    }

    public Pattern getSuccessPattern() {
        return successPattern;
    }
}
