package com.cps_assignment.backend;

import java.sql.Timestamp;

public class Currencies {
    private String symbol;
    private String name;
    private String flag;
    private double currencyChange;
    private double currencyRate;
    private Timestamp time;

    public String GetSymbol() {
        return symbol;
    }

    public String GetName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public double getCurrencyChange() {
        return currencyChange;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public Timestamp getTime() {
        return time;
    }
}