package org.bardframework.commons.waf;

import lombok.Getter;
import lombok.Setter;
import org.bardframework.commons.waf.extractor.RequestKeyDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "waf")
public class WafConfiguration {

    private List<RequestLimitChecker> rules;

    @Autowired
    private RequestCallCounter requestCallCounter;
    @Autowired
    private RequestKeyDetector requestKeyDetector;

    @Bean
    WafFilter wafFilter() {
        rules.forEach(rule -> {
            rule.setRequestCallCounter(requestCallCounter);
            rule.setRequestKeyDetector(requestKeyDetector);
        });
        return new WafFilter(rules);
    }
}
