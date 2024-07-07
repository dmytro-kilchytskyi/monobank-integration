package org.cloudstats.monobankintegration.model;

import lombok.Data;

@Data
public class CurrencyRate {
    public int currencyCodeA;
    public int currencyCodeB;
    public int data;
    public Float rateSell;
    public Float rateBuy;
    public Float rateCross;
}
