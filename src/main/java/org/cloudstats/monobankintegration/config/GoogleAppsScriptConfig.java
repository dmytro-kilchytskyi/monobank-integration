package org.cloudstats.monobankintegration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "monobank-integration-api.g-script")
public class GoogleAppsScriptConfig {
    private String url;
}
