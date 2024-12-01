package org.bardframework.commons.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SmsSender {

    String TO_KEY = "to";
    String MESSAGE_KEY = "message";

    default boolean send(String to, String message, Map<String, Object> args) throws IOException {
        args = new HashMap<>(args);
        args.put(TO_KEY, to);
        args.put(MESSAGE_KEY, message);
        return this.send(args);
    }

    boolean send(Map<String, Object> args) throws IOException;
}
