package com.cps_assignment.backend;


public class DataStripper {

    private static DataStripper instance;
    private Currencies currencies;

    public String stripHTML(){
        
        return "";
    }

    public static DataStripper getInstance(){
        if (instance == null)
            instance = new DataStripper();
        return instance;
    }
}
