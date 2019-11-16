package org.bardframework.commons.security.token.manager;

public interface Token {

    boolean isExpired(long tokenExpirationMills);
}
