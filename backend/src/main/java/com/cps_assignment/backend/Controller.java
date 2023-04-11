package com.cps_assignment.backend;

import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {

    public static void main(String[] args) throws Exception { // Pilot
        HTTPSniffer sniffer = new HTTPSniffer();
        DBCommunicator db = DBCommunicator.getDatabase();
        sniffer.sendGETRequest("https://www.valutakurser.dk/");

    }

}
