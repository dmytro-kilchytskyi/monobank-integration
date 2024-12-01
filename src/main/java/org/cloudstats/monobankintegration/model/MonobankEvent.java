package org.cloudstats.monobankintegration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonobankEvent {

    @lombok.Data
    public static class Data {
        private StatementItem statementItem;
    }

    @lombok.Data
    @Builder
    public static class StatementItem {
        private int time;
        private String description;
        private int amount;
        private int operationAmount;
        private int commissionRate;
    }

    private String type;
    private Data data;
}
