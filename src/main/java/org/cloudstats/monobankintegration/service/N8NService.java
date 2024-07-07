package org.cloudstats.monobankintegration.service;

import lombok.AllArgsConstructor;
import org.cloudstats.monobankintegration.config.N8NConfig;
import org.cloudstats.monobankintegration.model.Event;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class N8NService {
    private final N8NConfig n8NConfig;

    public <T> void trigger(Event<T> event) {
        // Create HttpHeaders object with basic auth credentials
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(n8NConfig.getUsername(), n8NConfig.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity with headers and body
        HttpEntity<Event<T>> requestEntity = new HttpEntity<>(event, headers);

        // Send POST request using RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(n8NConfig.getUrl(), HttpMethod.POST, requestEntity, String.class);
    }
}
