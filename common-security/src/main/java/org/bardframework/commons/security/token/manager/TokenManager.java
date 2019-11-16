package org.bardframework.commons.security.token.manager;


/**
 * Manages tokens
 * so we can implement and plug various policies.
 */
public interface TokenManager<T> {

    /**
     * Creates a new token for the user and returns it.
     * It may add it to the token list or replace the previous one for the user. Never returns {@code null}.
     */
    String put(T token);

    /**
     * Returns user details for a token.
     */
    T get(String tokenId);

    /**
     * Removes a single token.
     */
    boolean remove(String tokenId);
}
