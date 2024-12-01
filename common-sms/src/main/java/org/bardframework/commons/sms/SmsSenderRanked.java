package org.bardframework.commons.sms;

import lombok.Getter;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Getter
public class SmsSenderRanked implements SmsSender {
    private final List<SmsSender> senders;

    public SmsSenderRanked(List<SmsSender> senders) {
        this.senders = senders;
    }

    @Override
    public boolean send(Map<String, Object> args) throws IOException {
        Stack<SmsSender> sendersStack = new Stack<>();
        this.senders.stream().sorted(this.comparator()).forEach(sendersStack::add);
        return sendersStack.pop().send(args);
    }

    protected Comparator<SmsSender> comparator() {
        return (o1, o2) -> 0;
    }
}
