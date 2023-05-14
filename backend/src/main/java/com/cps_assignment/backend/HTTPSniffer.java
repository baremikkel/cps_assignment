package com.cps_assignment.backend;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * This class sends requests and receives responses from the api, extracts the new values and stores them in the database
 * @author Baremikkel
 */

@RestController
public class HTTPSniffer {
    private String date;
    private DBCommunicator db;

    public void sendGETRequest(String urlString, String date) throws Exception {
        //Sends the GET request to the api url and has the date with for later use.
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
        currencyExtractor(String.valueOf(content));
    }
    public void currencyExtractor(String jsonString) {
        //extracts all the key value pairs in the JSON array that was the response from the api
        JSONObject obj = new JSONObject(jsonString);
        JSONObject rates = obj.getJSONObject("rates");
        Iterator<String> keys = rates.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            double rate = rates.getDouble(key);
            addToDatabase(key, rate);
        }
    }

    private void addToDatabase(String key, double rate) {
        //Adds the data to the database, and is set up in the way if a new currency is supported in the api the database won't have problems storing the data
        try {
            db = DBCommunicator.getDatabase();
            db.updateTable("INSERT INTO currencies (currencysymbol) VALUES ('"+ key +"') ON CONFLICT (currencysymbol) DO NOTHING;");
            db.updateTable("INSERT INTO exchangeRates (currencyid, exchangevalue, lastexchange) VALUES(( SELECT currencyid FROM currencies WHERE currencysymbol = '"+ key +"'), '"+ rate +"', '" + date +"' );");
        } catch (SQLException e) {
            System.out.println("Fuck you thats why: " + e.getMessage());
        }
    }
}
