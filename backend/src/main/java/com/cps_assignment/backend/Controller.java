package com.cps_assignment.backend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class Controller {

    public static void main(String[] args) throws Exception {
        //URL url = new URL("https://api.zippopotam.us/DK/5000");
        URL url = new URL("https://api.coingecko.com/api/v3/exchange_rates");
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
    }


    @RequestMapping("/")
    public String controllerC(){
        return "";
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        return "Goodbye";
    }

}
