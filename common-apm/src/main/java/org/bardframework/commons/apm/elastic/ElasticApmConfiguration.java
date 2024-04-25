package org.bardframework.commons.apm.elastic;

import co.elastic.apm.attach.ElasticApmAttacher;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "bard.apm.elastic")
@Setter
public class ElasticApmConfiguration {

    private String enabled;
    private Map<String, String> elastic;

    @ConditionalOnProperty(name = "bard.apm.elastic.enabled", havingValue = "true")
    @PostConstruct
    void init() {
        if (!"true".equals(enabled)) {
            return;
        }
        elastic.remove("enabled");
        elastic.put("activation_method", "PROGRAMMATIC_SELF_ATTACH");
        ElasticApmAttacher.attach(elastic);
    }
}