package org.bardframework.commons.sms;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public abstract class SmsSenderHttpCallAbstract implements SmsSender {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderHttpCallAbstract.class);

    @Override
    public SendResult send(String receiverNumber, String message) {
        try {
            return this.getSmsSender(receiverNumber, message, null, null, null);
        } catch (Exception e) {
            LOGGER.error("error occurred in sms sender thread", e);
            return SendResult.ERROR;
        }
    }

    @Override
    public SendResult send(String receiverNumber, String message, String signature) {
        try {
            return this.getSmsSender(receiverNumber, message, signature, null, null);
        } catch (Exception e) {
            LOGGER.error("error occurred in sms sender thread", e);
            return SendResult.ERROR;
        }
    }

    @Override
    public SendResult send(String receiverNumber, String message, String username, String password) {
        try {
            return this.getSmsSender(receiverNumber, message, null, username, password);
        } catch (Exception e) {
            LOGGER.error("error occurred in sms sender thread", e);
            return SendResult.ERROR;
        }
    }

    private SendResult getSmsSender(String receiverNumber, String message, String signature, String username, String password) {
        LOGGER.info("sending sms to:  " + receiverNumber);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(this.fill(this.getUrl(), receiverNumber, message, signature, username, password, true)).openConnection();
            // optional default is GET
            connection.setRequestMethod(this.getHttpMethod());
            if (null != this.getContentType()) {
                connection.setRequestProperty("Content-Type", this.getContentType());
            }
            if (null != this.getBody() && ("PUT".equalsIgnoreCase(this.getHttpMethod()) || "POST".equalsIgnoreCase(this.getHttpMethod()))) {
                LOGGER.debug(String.format("send sms http method is %s setting request body", this.getHttpMethod()));
                connection.setDoOutput(true);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(this.fill(this.getBody(), receiverNumber, message, signature, username, password, false).getBytes(StandardCharsets.UTF_8));
                }
            } else {
                LOGGER.debug("send sms http method is not PUT or POST or request body is null, not set body");
            }

            String result = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
            LOGGER.info(String.format("Sending '%s' request to %s, code: %s, response: %s", this.getHttpMethod(), this.getUrl(), connection.getResponseCode(), result));
            if (null != this.getInsufficientCreditPattern() && Pattern.matches(this.getInsufficientCreditPattern(), result)) {
                return SendResult.INSUFFICIENT_CREDIT;
            }
            if (null != this.getSuccessPattern()) {
                if (Pattern.matches(this.getSuccessPattern(), result)) {
                    return SendResult.SUCCESS;
                } else {
                    return SendResult.ERROR;
                }
            }
            return SendResult.SUCCESS;
        } catch (Exception e) {
            LOGGER.error("error sending sms", e);
            return SendResult.ERROR;
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    private String fill(String urlTemplate, String to, String message, String signature, String username, String password, boolean urlEncode) throws UnsupportedEncodingException {
        String result = String.valueOf(urlTemplate);
        if (null != to) {
            result = result.replaceAll(":to", urlEncode ? URLEncoder.encode(to, StandardCharsets.UTF_8.displayName()) : to);
        }
        if (null != message) {
            result = result.replaceAll(":message", urlEncode ? URLEncoder.encode(message, StandardCharsets.UTF_8.displayName()) : message);
        }
        if (null != signature) {
            result = result.replaceAll(":signature", urlEncode ? URLEncoder.encode(signature, StandardCharsets.UTF_8.displayName()) : signature);
        }
        if (null != username) {
            result = result.replaceAll(":username", urlEncode ? URLEncoder.encode(username, StandardCharsets.UTF_8.displayName()) : username);
        }
        if (null != password) {
            result = result.replaceAll(":password", urlEncode ? URLEncoder.encode(password, StandardCharsets.UTF_8.displayName()) : password);
        }
        return result;
    }

    public abstract String getHttpMethod();

    public abstract String getUrl();

    public abstract String getSuccessPattern();

    public abstract String getInsufficientCreditPattern();

    public abstract String getContentType();

    public abstract String getBody();
}
