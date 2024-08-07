package org.bardframework.commons.security.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bardframework.commons.security.token.manager.Token;
import org.springframework.security.core.Authentication;

/**
 * Contains information about authentication.
 */
@Getter
@Setter
public abstract class AuthenticationAbstract<U> implements Authentication, Token {
    protected U user;
    protected long created;

    public AuthenticationAbstract() {
    }

    public AuthenticationAbstract(U user) {
        this.user = user;
        this.created = System.currentTimeMillis();
    }

    @Override
    public boolean isExpired(long validAgeMills) {
        return this.created + validAgeMills <= System.currentTimeMillis();
    }

    @JsonIgnore
    @Override
    public Object getCredentials() {
        return "pass***";
    }

    @JsonIgnore
    @Override
    public Object getDetails() {
        return user;
    }

    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return user;
    }

    @JsonIgnore
    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @JsonIgnore
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @JsonIgnore
    @Override
    public String getName() {
        return null == user ? null : user.toString();
    }
}
