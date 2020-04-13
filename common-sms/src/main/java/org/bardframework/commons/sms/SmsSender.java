package org.bardframework.commons.sms;

public interface SmsSender {
    SendResult send(String receiverNumber, String message);

    SendResult send(String receiverNumber, String message, String signature);

    SendResult send(String receiverNumber, String message, String username, String password);

    enum SendResult {
        SUCCESS, INSUFFICIENT_CREDIT, ERROR
    }
}
