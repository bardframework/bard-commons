package org.bardframework.commons.waf;

import java.util.concurrent.TimeUnit;

public interface RequestCallCounter {

    Long increment(String key);

    void expire(String key, int expiration, TimeUnit unit);
}
