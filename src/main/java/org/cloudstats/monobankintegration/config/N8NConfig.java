package org.cloudstats.monobankintegration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "monobank-integration-api.n8n")
public class N8NConfig {
    private String username;
    private String password;
    private String url;
}
