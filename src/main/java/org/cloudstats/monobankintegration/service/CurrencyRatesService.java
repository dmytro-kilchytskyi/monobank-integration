package org.cloudstats.monobankintegration.service;

import lombok.AllArgsConstructor;
import org.cloudstats.monobankintegration.model.CurrencyRate;
import org.cloudstats.monobankintegration.model.Event;
import org.cloudstats.monobankintegration.model.EventType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CurrencyRatesService {
    private final N8NService n8nService;

    @Scheduled(cron = "@daily")
    public void triggerN8N() {
        List<CurrencyRate> rates = getCurrencyRates();
        Event<List<CurrencyRate>> event = new Event<>(EventType.RATE, rates);

        n8nService.trigger(event);
    }

    private List<CurrencyRate> getCurrencyRates() {
        return WebClient.create().get()
                .uri("https://api.monobank.ua/bank/currency")
                .retrieve()
                .bodyToFlux(CurrencyRate.class)
                .filter(rate -> Objects.nonNull(rate.getRateSell()) && rate.getCurrencyCodeB() == 980)
                .collectList()
                .block();
    }
}
