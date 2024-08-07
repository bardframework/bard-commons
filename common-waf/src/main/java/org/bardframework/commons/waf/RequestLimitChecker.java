package org.bardframework.commons.waf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.waf.exception.CallLimitExceedException;
import org.bardframework.commons.waf.extractor.RequestKeyDetector;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class RequestLimitChecker {

    private static final String PREFIX = "CALL_LIMITER_";

    private final AntPathRequestMatcher requestMatcher;
    private final RequestCallCounter requestCallCounter;
    private final RequestKeyDetector requestKeyDetector;
    private final HttpMethod httpMethod;
    private final int limit;
    private final int period;
    private final TimeUnit periodUnit;
    private Set<String> whiteList = new HashSet<>();

    /**
     * post method
     */
    public RequestLimitChecker(String url, RequestCallCounter requestCallCounter, RequestKeyDetector requestKeyDetector, int limit, int period, TimeUnit periodUnit) {
        this.httpMethod = null;
        this.requestMatcher = new AntPathRequestMatcher(url);
        this.requestCallCounter = requestCallCounter;
        this.requestKeyDetector = requestKeyDetector;
        this.limit = limit;
        this.period = period;
        this.periodUnit = periodUnit;
    }

    public RequestLimitChecker(HttpMethod httpMethod, String url, RequestCallCounter requestCallCounter, RequestKeyDetector requestKeyDetector, int limit, int period, TimeUnit periodUnit) {
        this.httpMethod = httpMethod;
        this.requestMatcher = new AntPathRequestMatcher(url, httpMethod.name());
        this.requestCallCounter = requestCallCounter;
        this.requestKeyDetector = requestKeyDetector;
        this.limit = limit;
        this.period = period;
        this.periodUnit = periodUnit;
    }

    public void checkCallLimit(HttpServletRequest request, HttpServletResponse response) throws CallLimitExceedException {
        String key = requestKeyDetector.getUniqueKey(request, response);
        if (null == key) {
            log.warn("can't detect unique key of request [{} {}] for checking call limit", request.getMethod(), request.getRequestURI());
            return;
        }
        if (this.whiteList.contains(key)) {
            log.debug("request [{} {}] with unique key[{}] not checked, unique key is in white list", request.getMethod(), request.getRequestURI(), key);
            return;
        }
        key = PREFIX + key + (null == httpMethod ? "" : "@" + httpMethod) + "@" + requestMatcher.getPattern();
        long count = requestCallCounter.increment(key);
        if (count > limit) {
            throw new CallLimitExceedException(key);
        }
        if (count == 1) {
            requestCallCounter.expire(key, period, periodUnit);
        }
    }

    public boolean match(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    public void setWhiteList(String... whiteList) {
        this.whiteList = new HashSet<>(List.of(whiteList));
    }

}
