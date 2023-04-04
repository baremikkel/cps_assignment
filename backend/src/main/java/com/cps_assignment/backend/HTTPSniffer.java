package com.cps_assignment.backend;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class HTTPSniffer {
    private String htmlDump;
    private DataStripper stripper;

    public void sendGETRequest(String urlString) throws Exception{
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
        htmlDump = content.toString();
        stripper = DataStripper.getInstance();
        stripper.stripHTML(htmlDump);
    }
}
