package org.bardframework.commons.waf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RequestCallCounterInMemory implements RequestCallCounter {
    private final Map<String, Long> callCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> expireTimes = new ConcurrentHashMap<>();

    @Override
    public Long increment(String key) {
        Long expireTime = this.expireTimes.get(key);
        /*
            first time key insert
         */
        if (null == expireTime) {
            this.callCounts.put(key, 1L);
            return 1L;
        }
        /*
            key exist, but expired
         */
        if (expireTime <= System.currentTimeMillis()) {
            this.callCounts.put(key, 1L);
            return 1L;
        }
        Long incrementedValue = this.callCounts.get(key) + 1;
        this.callCounts.put(key, incrementedValue);
        return incrementedValue;
    }

    @Override
    public void expire(String key, int expiration, TimeUnit unit) {
        this.expireTimes.put(key, System.currentTimeMillis() + unit.toMillis(expiration));
    }
}
