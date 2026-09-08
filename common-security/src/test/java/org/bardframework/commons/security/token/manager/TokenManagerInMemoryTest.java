package org.bardframework.commons.security.token.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TokenManagerInMemoryTest {

    private static class TestToken implements Token {
        private final long issuedAt;

        TestToken(long issuedAt) {
            this.issuedAt = issuedAt;
        }

        @Override
        public boolean isExpired(long tokenExpirationMills) {
            return System.currentTimeMillis() - issuedAt > tokenExpirationMills;
        }
    }

    private static TokenManagerInMemory<TestToken> managerWithExpiration(long expirationMills) {
        return new TokenManagerInMemory<>(expirationMills) {
        };
    }

    @Test
    void put_thenGet_returnsTheSameToken() {
        TokenManagerInMemory<TestToken> manager = managerWithExpiration(60_000);
        TestToken token = new TestToken(System.currentTimeMillis());

        String id = manager.put(token);

        assertThat(id).isNotBlank();
        assertThat(manager.get(id)).isSameAs(token);
    }

    @Test
    @DisplayName("token ids are unique — a collision would hand one user another's session")
    void put_generatesUniqueIds() {
        TokenManagerInMemory<TestToken> manager = managerWithExpiration(60_000);
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < 500; i++) {
            ids.add(manager.put(new TestToken(System.currentTimeMillis())));
        }
        assertThat(ids).hasSize(500);
    }

    @Test
    void get_returnsNullForUnknownId() {
        assertThat(managerWithExpiration(60_000).get("does-not-exist")).isNull();
    }

    @Test
    @DisplayName("an expired token is not returned")
    void get_returnsNullForExpiredToken() {
        TokenManagerInMemory<TestToken> manager = managerWithExpiration(0);
        String id = manager.put(new TestToken(System.currentTimeMillis() - 10_000));

        assertThat(manager.get(id)).isNull();
    }

    @Test
    void remove_deletesTheToken() {
        TokenManagerInMemory<TestToken> manager = managerWithExpiration(60_000);
        String id = manager.put(new TestToken(System.currentTimeMillis()));

        assertThat(manager.remove(id)).isTrue();
        assertThat(manager.get(id)).isNull();
    }

    @Test
    void remove_reportsFalseForUnknownId() {
        assertThat(managerWithExpiration(60_000).remove("does-not-exist")).isFalse();
    }
}
