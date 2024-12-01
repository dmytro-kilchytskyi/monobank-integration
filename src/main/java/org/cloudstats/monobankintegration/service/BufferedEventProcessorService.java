package org.cloudstats.monobankintegration.service;

import lombok.AllArgsConstructor;
import org.cloudstats.monobankintegration.model.MonobankEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class BufferedEventProcessorService {
    private final GoogleAppsScriptService googleAppsScriptService;

    private final List<MonobankEvent> buffer = Collections.synchronizedList(new ArrayList<>());

    public void process(MonobankEvent monobankEvent) {
        buffer.add(monobankEvent);
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    private void flushBuffer() {
        if (buffer.isEmpty()) return;

        MonobankEvent firstEvent = buffer.remove(0);
        MonobankEvent.StatementItem accumulator = firstEvent.getData().getStatementItem();

        buffer.forEach(e -> {
            MonobankEvent.StatementItem statementItem = e.getData().getStatementItem();
            accumulator.setCommissionRate(accumulator.getCommissionRate() + statementItem.getCommissionRate());
            accumulator.setAmount(accumulator.getAmount() + statementItem.getAmount());
            accumulator.setOperationAmount(accumulator.getOperationAmount() + statementItem.getOperationAmount());
        });
        buffer.clear();

        googleAppsScriptService.executeScript(firstEvent);
    }
}
