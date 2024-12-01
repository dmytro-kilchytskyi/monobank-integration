package org.cloudstats.monobankintegration.rest;

import lombok.AllArgsConstructor;
import org.cloudstats.monobankintegration.model.Event;
import org.cloudstats.monobankintegration.model.EventType;
import org.cloudstats.monobankintegration.model.MonobankEvent;
import org.cloudstats.monobankintegration.service.BufferedEventProcessorService;
import org.cloudstats.monobankintegration.service.N8NService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class WebHookController {
    private final N8NService n8nService;
    private final BufferedEventProcessorService bufferedEventProcessorService;

    @GetMapping("/healthz")
    public void healthCheck() {}

    @PostMapping
    public void processEvent(@RequestBody MonobankEvent event) {
        if (event.getData().getStatementItem().getDescription().equals("Банкомат OTP")) {
            bufferedEventProcessorService.process(event);
        } else {
            n8nService.trigger(new Event(EventType.LOG, event));
        }
    }
}
