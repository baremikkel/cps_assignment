package com.cps_assignment.backend;


import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;

public class DataStripper {

    private static DataStripper instance;
    private Currencies currencies = new Currencies();
    private DBCommunicator db;

    public void stripHTML(String dump) { //Tager kun det element hvor hvad vi skal bliver lagret
        Document doc = Jsoup.parse(dump);
        String s = doc.getElementById("__NEXT_DATA__").toString();
        // System.out.println(s);
        extractSortedArray(s);
    }

    public static DataStripper getInstance() { //Singleton
        if (instance == null)
            instance = new DataStripper();
        return instance;
    }

    private void extractSortedArray(String s) { // fjerner alt hvad vi ikke skal bruge og bruger Gson til at formaterer JSON til java object classes
        s = s.substring(s.indexOf("\"sorted\":") + 10);
        s = s.substring(0, s.indexOf("]"));
        String[] strings = s.split("},");
        Gson gson = new Gson();
        for (int i = 0; i < strings.length - 1; i++) {
            strings[i] = strings[i] + "}";
            addToDatabase(gson, strings[i]);

        }
        System.out.println(currencies.getCurrenciesArrayList().size());
    }

    private void addToDatabase(Gson gson, String s) { //tilføjer data til databasen
        currencies = gson.fromJson(s, Currencies.class);
        try {
            db = DBCommunicator.getDatabase();
            System.out.println(currencies.toString());
            db.UpdateTable("INSERT INTO currencies (currencyname, currencysymbol) VALUES ('"+ currencies.GetName()+"', '"+currencies.GetSymbol()+"') ON CONFLICT (currencyname) DO NOTHING;");
            db.UpdateTable("INSERT INTO exchangeRates (currencyid, exchangevalue, lastexchange) VALUES(( SELECT currencyid FROM currencies WHERE currencyname = '"+ currencies.GetName() +"'), '"+ currencies.getActualValue() +"', '" +currencies.getDate() +"' ) ON CONFLICT (lastexchange) DO NOTHING;");
        } catch (SQLException e) {
            System.out.println("Fuck you thats why: " + e.getMessage());
        }
    }
}

