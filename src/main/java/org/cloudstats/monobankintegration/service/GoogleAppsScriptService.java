package org.cloudstats.monobankintegration.service;

import lombok.RequiredArgsConstructor;
import org.cloudstats.monobankintegration.config.GoogleAppsScriptConfig;
import org.cloudstats.monobankintegration.model.MonobankEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GoogleAppsScriptService {
    private final GoogleAppsScriptConfig config;
    public void executeScript(MonobankEvent e) {
        WebClient.create().post()
                .uri(config.getUrl())
                .bodyValue(e)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
