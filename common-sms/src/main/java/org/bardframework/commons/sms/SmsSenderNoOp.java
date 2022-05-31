package org.bardframework.commons.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SmsSenderNoOp implements SmsSender {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderNoOp.class);

    @Override
    public boolean send(Map<String, String> args) {
        LOGGER.warn("sms sender not configured.");
        return false;
    }
}
