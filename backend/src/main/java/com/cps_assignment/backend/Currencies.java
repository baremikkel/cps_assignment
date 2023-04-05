package com.cps_assignment.backend;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Currencies {
    private String currencyName;
    private String symbol;
    private String flagName;
    private double currencyChange; //Hvor mange procent valutaen er faldet med siden sidste datapoint
    private double actualValue; //# danske kroner for 100 af currency
    private String date; //tid hvornår data blev opdateret

    private ArrayList<Currencies> currenciesArrayList = new ArrayList<>();


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

    public ArrayList<Currencies> getCurrenciesArrayList() {
        return currenciesArrayList;
    }

    @Override
    public String toString() {
        return  "currencyName='" + currencyName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", flagName='" + flagName + '\'' +
                ", currencyChange=" + currencyChange +
                ", actualValue=" + actualValue +
                ", date='" + date + '\'';
    }


}