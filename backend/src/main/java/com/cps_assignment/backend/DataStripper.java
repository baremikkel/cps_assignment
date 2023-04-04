package com.cps_assignment.backend;


import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class DataStripper {

    private static DataStripper instance;
    private Currencies currencies;

    public String stripHTML(String dump) {
        Document doc = Jsoup.parse(dump);
        String s = doc.getElementById("__NEXT_DATA__").toString();
        // System.out.println(s);
        extractSortedArray(s);
        return "";
    }

    public static DataStripper getInstance() {
        if (instance == null)
            instance = new DataStripper();
        return instance;
    }

    private void extractSortedArray(String s) {
        s = s.substring(s.indexOf("\"sorted\":") + 10);
        s = s.substring(0, s.indexOf("]"));
        String[] strings = s.split("},");
        for (int i = 0; i < strings.length-1; i++) {
           // strings[i] = strings[i].substring(1);
            strings[i] = strings[i]+"}";
        }
        Gson gson = new Gson();

        currencies = gson.fromJson(strings[6], Currencies.class);
        System.out.println(currencies.toString());
    }
}

