package com.cps_assignment.backend;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;

@RestController
public class HTTPSniffer {
    private String date;
    private DBCommunicator db;

    public void sendGETRequest(String urlString, String date) throws Exception { //Sender en GET Request fra det url som er givet som parameter
        this.date = date;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            content.append(inputLine);
        }
        br.close();
        System.out.println("test");
        currencyExtractor(String.valueOf(content));

       /* System.out.println("Recived request, and sending to stripper");
        stripper = DataStripper.getInstance();
        stripper.stripHTML(content.toString());*/
    }
    public void currencyExtractor(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        JSONObject rates = obj.getJSONObject("rates");
        Iterator<String> keys = rates.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            double rate = rates.getDouble(key);
            addToDatabase(key, rate);
        }
    }

    private void addToDatabase(String key, double rate) { //tilføjer data til databasen
        try {
            db = DBCommunicator.getDatabase();
            db.UpdateTable("INSERT INTO currencies (currencysymbol) VALUES ('"+ key +"') ON CONFLICT (currencysymbol) DO NOTHING;");
            db.UpdateTable("INSERT INTO exchangeRates (currencyid, exchangevalue, lastexchange) VALUES(( SELECT currencyid FROM currencies WHERE currencysymbol = '"+ key +"'), '"+ rate +"', '" + date +"' );");
        } catch (SQLException e) {
            System.out.println("Fuck you thats why: " + e.getMessage());
        }
    }

}
