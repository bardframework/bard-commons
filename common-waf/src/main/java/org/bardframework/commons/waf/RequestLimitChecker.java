package org.bardframework.commons.waf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.waf.exception.CallLimitExceedException;
import org.bardframework.commons.waf.extractor.RequestKeyDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Slf4j
public class RequestLimitChecker {

    private static final String PREFIX = "CALL_LIMITER_";

    private AntPathRequestMatcher requestMatcher;
    private HttpMethod httpMethod;
    private int limit;
    private int period;
    private TimeUnit periodUnit;
    private Set<String> whiteList = new HashSet<>();
    @Autowired
    private RequestCallCounter requestCallCounter;
    @Autowired
    private RequestKeyDetector requestKeyDetector;

    public RequestLimitChecker() {
    }

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
        String key = this.getRequestKeyDetector().getUniqueKey(request, response);
        if (null == key) {
            log.warn("can't detect unique key of request [{} {}] for checking call limit", request.getMethod(), request.getRequestURI());
            return;
        }
        if (this.getWhiteList().contains(key)) {
            log.debug("request [{} {}] with unique key[{}] not checked, unique key is in white list", request.getMethod(), request.getRequestURI(), key);
            return;
        }
        key = PREFIX + key + (null == this.getHttpMethod() ? "" : "@" + this.getHttpMethod()) + "@" + this.getRequestMatcher().getPattern();
        long count = this.getRequestCallCounter().increment(key);
        if (count > this.getLimit()) {
            throw new CallLimitExceedException(key);
        }
        if (count == 1) {
            this.getRequestCallCounter().expire(key, this.getPeriod(), this.getPeriodUnit());
        }
    }

    public boolean match(HttpServletRequest request) {
        return this.getRequestMatcher().matches(request);
    }

    public void setWhiteList(String... whiteList) {
        this.whiteList = new HashSet<>(List.of(whiteList));
    }

    public void setUrl(String url) {
        this.requestMatcher = new AntPathRequestMatcher(url);
    }
}
