package org.bardframework.commons.security.token.manager;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again,
 * older token is invalidated.
 */
@Getter
public abstract class TokenManagerInMemory<T extends Token> implements TokenManager<T> {

    protected final Map<String, T> tokensMap = new ConcurrentHashMap<>();
    /**
     * millisecond token expire time
     */
    protected final long tokenExpirationMills;

    protected TokenManagerInMemory(long tokenExpirationMills) {
        this.tokenExpirationMills = tokenExpirationMills;
    }

    @Override
    public String put(T token) {
        String tokenId = UUID.randomUUID() + "-" + System.nanoTime();
        this.getTokensMap().put(tokenId, token);
        return tokenId;
    }

    @Override
    public boolean remove(String tokenId) {
        return this.getTokensMap().remove(tokenId) != null;
    }

    @Override
    public T get(String tokenId) {
        T tokenInfo = this.getTokensMap().get(tokenId);
        if (null == tokenInfo) {
            return null;
        }
        if (tokenInfo.isExpired(this.getTokenExpirationMills())) {
            this.remove(tokenId);
            return null;
        }
        return tokenInfo;
    }

    /**
     * clean expired tokens
     */
    @Scheduled(cron = "${token.manager.cleaner.cron:0 */1 * * * *}")
    public void cleanExpired() {
        this.getTokensMap().entrySet().parallelStream().filter(entry -> entry.getValue().isExpired(this.getTokenExpirationMills())).forEach(entry -> this.getTokensMap().remove(entry.getKey()));
    }
}