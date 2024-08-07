package org.bardframework.commons.sms;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SmsSenderNoOp implements SmsSender {

    @Override
    public boolean send(Map<String, String> args) {
        log.warn("sms sender not configured.");
        return true;
    }
}
