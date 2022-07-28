package org.bardframework.commons.sms;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SmsSenderRanked implements SmsSender {
    private final List<SmsSender> senders;

    public SmsSenderRanked(List<SmsSender> senders) {
        this.senders = senders;
    }

    public List<SmsSender> getSenders() {
        return senders;
    }

    @Override
    public boolean send(Map<String, String> args) throws IOException {
        Stack<SmsSender> sendersStack = new Stack<>();
        this.senders.stream().sorted(this.comparator()).forEach(sendersStack::add);
        boolean sent = sendersStack.pop().send(args);
        return sent;
    }

    protected Comparator<SmsSender> comparator() {
        return (o1, o2) -> 0;
    }
}
