package org.cloudstats.monobankintegration.rest;

import org.cloudstats.monobankintegration.model.Event;
import org.cloudstats.monobankintegration.model.EventType;
import org.cloudstats.monobankintegration.model.MonobankEvent;
import org.cloudstats.monobankintegration.service.GoogleAppsScriptService;
import org.cloudstats.monobankintegration.service.N8NService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@WebFluxTest(WebHookController.class)
public class WebHookControllerTest {
    @Autowired
    private WebHookController controller;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GoogleAppsScriptService googleAppsScriptService;

    @MockBean
    private N8NService n8nService;

    @Test
    public void shouldCreateWebHook() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldPassHealthCheck() throws Exception {
        webTestClient.get()
                .uri("/api/v1/healthz")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void shouldExecuteGoogleAppsScript() throws Exception {
        MonobankEvent monobankEvent = stubWithdrawEvent("Банкомат OTP");

        webTestClient.post()
                .uri("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(monobankEvent)
                .exchange()
                .expectStatus()
                .isOk();

        verify(googleAppsScriptService).executeScript(monobankEvent);
    }

    @Test
    public void shouldTriggerN8NWebhook() throws Exception {
        MonobankEvent monobankEvent = stubWithdrawEvent("Custom event");
        Event event = new Event(EventType.LOG, monobankEvent);

        webTestClient.post()
                .uri("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(monobankEvent)
                .exchange()
                .expectStatus()
                .isOk();

        verify(n8nService).trigger(event);
    }

    private static MonobankEvent stubWithdrawEvent(String description) {
        MonobankEvent.StatementItem statementItem = MonobankEvent.StatementItem.builder()
                .time(99999)
                .description(description)
                .amount(1000)
                .operationAmount(1000)
                .commissionRate(10)
                .build();
        MonobankEvent.Data data = new MonobankEvent.Data();
        data.setStatementItem(statementItem);

        return MonobankEvent.builder()
                .type("StatementItem")
                .data(data).build();
    }
}
