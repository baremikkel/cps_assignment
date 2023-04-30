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


    public void decideOnEndpoint() {
    }
    public void sendGETRequest(String urlString) throws Exception { //Sender en GET Request fra det url som er givet som parameter
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
        System.out.println(content.toString());

       /* System.out.println("Recived request, and sending to stripper");
        stripper = DataStripper.getInstance();
        stripper.stripHTML(content.toString());*/
    }

    private void getLatest(String urlString) {}
}
