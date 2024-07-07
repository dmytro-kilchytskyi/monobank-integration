package org.cloudstats.monobankintegration.rest;

import lombok.AllArgsConstructor;
import org.cloudstats.monobankintegration.model.Event;
import org.cloudstats.monobankintegration.model.EventType;
import org.cloudstats.monobankintegration.model.MonobankEvent;
import org.cloudstats.monobankintegration.service.GoogleAppsScriptService;
import org.cloudstats.monobankintegration.service.N8NService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class WebHookController {
    private final N8NService n8nService;
    private final GoogleAppsScriptService googleAppsScriptService;

    @GetMapping
    public void validate() {}

    @PostMapping
    public void processEvent(@RequestBody MonobankEvent event) {
        if (event.getData().getStatementItem().getDescription().equals("Банкомат OTP")) {
            googleAppsScriptService.executeScript(event);
            System.out.println("-> script triggered successfully🙂");
        } else {
            n8nService.trigger(new Event<>(EventType.LOG, event));
            System.out.println("-> event logged successfully🙂");
        }
    }
}
