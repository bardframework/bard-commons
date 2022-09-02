package org.bardframework.commons.web.cors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CorsFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);

    private final RequestMatcher corsRequestMatchers;
    private final AntPathMatcher antPathMatcher;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowedCredentials;
    private List<String> exposedHeaders;
    private int maxAge;

    public CorsFilter(List<String> corsMapping) {
        this.corsRequestMatchers = new OrRequestMatcher(corsMapping.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
        this.antPathMatcher = new AntPathMatcher();
        this.antPathMatcher.setTrimTokens(false);
        this.antPathMatcher.setCaseSensitive(false);
    }

    public CorsFilter(List<String> corsMapping, List<String> allowedOrigins, List<String> allowedMethods, List<String> allowedHeaders, List<String> exposedHeaders, boolean allowedCredentials, int maxAge) {
        this(corsMapping);
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
        this.allowedCredentials = allowedCredentials;
        this.exposedHeaders = exposedHeaders;
        this.maxAge = maxAge;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader("Origin");
        if (StringUtils.isBlank(origin) || !this.getCorsRequestMatchers().matches(request)) {
            chain.doFilter(request, response);
            return;
        }
        if (this.isAllowedOrigin(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        if (null != this.getAllowedMethods()) {
            response.setHeader("Access-Control-Allow-Methods", String.join(",", this.getAllowedMethods()));
        }
        if (0 < this.getMaxAge()) {
            response.setHeader("Access-Control-Max-Age", String.valueOf(this.getMaxAge()));
        }
        if (null != this.getAllowedHeaders()) {
            response.setHeader("Access-Control-Allow-Headers", String.join(",", this.getAllowedHeaders()));
        }
        if (this.isAllowedCredentials()) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        if (null != this.getExposedHeaders()) {
            response.addHeader("Access-Control-Expose-Headers", String.join(",", this.getExposedHeaders()));
        }
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAllowedOrigin(String origin) {
        return this.getAllowedOrigins().stream().anyMatch(allowedOrigin -> this.getAntPathMatcher().match(allowedOrigin, origin));
    }

    public RequestMatcher getCorsRequestMatchers() {
        return corsRequestMatchers;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public boolean isAllowedCredentials() {
        return allowedCredentials;
    }

    public List<String> getExposedHeaders() {
        return exposedHeaders;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public AntPathMatcher getAntPathMatcher() {
        return antPathMatcher;
    }
}
