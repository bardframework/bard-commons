package org.bardframework.commons.security.token.manager;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again,
 * older token is invalidated.
 */
public abstract class InMemoryTokenManager<T extends Token> implements TokenManager<T> {

    private final Map<String, T> tokens = new ConcurrentHashMap<>();
    /**
     * millisecond token expire time
     */
    private long tokenExpirationMills = 5 * 24 * 60 * 60 * 1000;

    @Override
    public String put(T token) {
        String tokenId = UUID.randomUUID().toString() + "-" + System.nanoTime();
        tokens.put(tokenId, token);
        return tokenId;
    }

    @Override
    public boolean remove(String tokenId) {
        return tokens.remove(tokenId) != null;
    }

    @Override
    public T get(String tokenId) {
        T tokenInfo = tokens.get(tokenId);
        if (null == tokenInfo) {
            return null;
        }
        if (tokenInfo.isExpired(tokenExpirationMills)) {
            this.remove(tokenId);
            return null;
        }
        return tokenInfo;
    }

    public void setTokenExpirationMills(long tokenExpirationMills) {
        this.tokenExpirationMills = tokenExpirationMills;
    }

    /**
     * clean expired tokens
     */
    @Scheduled(cron = "${token.manager.cleaner.cron:0 */1 * * * *}")
    public void cleanTempFolder() {
        tokens.entrySet().parallelStream().filter(entry -> entry.getValue().isExpired(tokenExpirationMills)).forEach(entry -> tokens.remove(entry.getKey()));
    }
}