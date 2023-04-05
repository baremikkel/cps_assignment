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

    public static void main(String[] args) throws Exception { // Pilot
        HTTPSniffer sniffer = new HTTPSniffer();
        sniffer.sendGETRequest("https://www.valutakurser.dk/");

    }

}
