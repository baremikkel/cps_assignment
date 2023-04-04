package com.cps_assignment.backend;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Currencies {
    private String currencyName;
    private String symbol;
    private String flagName;
    private double currencyChange;
    private double actualValue;
    private String date;

    private ArrayList<Currencies> currenciesArrayList;

    public String GetSymbol() {
        return symbol;
    }
    public String GetName() {
        return currencyName;
    }

    public String getFlagName() {
        return flagName;
    }

    public double getCurrencyChange() {
        return currencyChange;
    }

    public double getActualValue() {
        return actualValue;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "currencyName='" + currencyName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", flagName='" + flagName + '\'' +
                ", currencyChange=" + currencyChange +
                ", actualValue=" + actualValue +
                ", date='" + date + '\'' +
                '}';
    }


}