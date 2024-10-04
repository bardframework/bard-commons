package org.bardframework.commons.sms;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.http.HttpCallResponse;
import org.bardframework.commons.utils.http.HttpCaller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Getter
public class SmsSenderHttpCall extends HttpCaller implements SmsSender {
    protected Pattern successPattern;

    public SmsSenderHttpCall() {
    }

    public SmsSenderHttpCall(String httpMethod, String urlTemplate, String successPattern) {
        super(httpMethod, urlTemplate);
        this.setSuccessPattern(successPattern);
    }

    public void setSuccessPattern(String successPattern) {
        this.successPattern = Pattern.compile(successPattern);
    }

    @Override
    public final boolean send(Map<String, String> args) throws IOException {
        String receiverNumberForLog = StringUtils.overlay(args.get("to"), "*", 4, args.get("to").length() - 4);
        log.info("try sending sms to: {}", receiverNumberForLog);
        HttpCallResponse callResult = this.call(this.prepareHeadersForSend(), args);
        log.info("http status of sending sms to [{}] is [{}]", receiverNumberForLog, callResult.getStatusCode());
        return this.isSuccess(callResult, receiverNumberForLog, args);
    }

    protected boolean isSuccess(HttpCallResponse result, String receiverNumberForLog, Map<String, String> args) throws IOException {
        if (ArrayUtils.isNotEmpty(result.getError())) {
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

}
