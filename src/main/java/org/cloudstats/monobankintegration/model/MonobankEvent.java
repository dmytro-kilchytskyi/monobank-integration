package org.cloudstats.monobankintegration.model;

import lombok.Data;

@Data
public class MonobankEvent {

    @lombok.Data
    public static class Data {
        private StatementItem statementItem;
    }

    @lombok.Data
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
