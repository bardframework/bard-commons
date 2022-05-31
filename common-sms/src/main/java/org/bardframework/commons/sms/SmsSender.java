package org.bardframework.commons.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SmsSender {
    default boolean send(String to, String message, Map<String, String> args) throws IOException {
        args = new HashMap<>(args);
        args.put("to", to);
        args.put("message", message);
        return this.send(args);
    }

    boolean send(Map<String, String> args) throws IOException;
}
